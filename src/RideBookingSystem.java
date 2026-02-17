import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class RideBookingSystem {

    private List<User> users;
    public List<Ride> rideList;
    private List<Booking> bookings;

    public RideBookingSystem() {
        this.users = FileManager.loadUsers();
        this.rideList = FileManager.loadRides(this.users);
        this.bookings = FileManager.loadBookings(this.users, this.rideList);
    }

    // --- USERS ---
    public List<User> getUsers() { return users; }

    public boolean signup(String name, String email, String password) {
        if (name == null || email == null || password == null) return false;
        // duplicate email check (case-insensitive)
        for (User u : users) if (u.getEmail().equalsIgnoreCase(email)) return false;
        int id = nextUserId();
        User newUser = new User(id, name, email, password);
        users.add(newUser);
        FileManager.saveUsers(users);
        return true;
    }

    public User login(String email, String password) {
        if (email == null || password == null) return null;
        for (User u : users) {
            if (u.getEmail().equalsIgnoreCase(email) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    private int nextUserId() {
        return users.stream().map(User::getId).max(Comparator.naturalOrder()).orElse(0) + 1;
    }

    // --- RIDES ---
    public void createRide(String source, String destination, int seats, double fare, User driver) {
        int id = nextRideId();
        Ride r = new Ride(id, source, destination, seats, fare, driver);
        rideList.add(r);
        FileManager.saveRides(rideList);
    }

    private int nextRideId() {
        return rideList.stream().map(Ride::getId).max(Comparator.naturalOrder()).orElse(0) + 1;
    }

    public List<Ride> showAllRide() { return rideList; }

    public List<Ride> searchRide(String source, String destination, int seats) {
        List<Ride> res = new ArrayList<>();
        if (source == null || destination == null) return res;
        for (Ride r : rideList) {
            if (r.getSource().equalsIgnoreCase(source.trim())
                    && r.getDestination().equalsIgnoreCase(destination.trim())
                    && r.getSeats() >= seats) {
                res.add(r);
            }
        }
        return res;
    }

    // --- BOOKINGS ---
    public boolean bookRide(int rideId, User user, int seatsToBook) {
        for (Ride r : rideList) {
            if (r.getId() == rideId) {
                if (seatsToBook <= 0) return false;
                if (r.getSeats() < seatsToBook) return false;
                r.setSeats(r.getSeats() - seatsToBook);
                int bookingId = nextBookingId();
                Booking b = new Booking(bookingId, r, user, seatsToBook);
                bookings.add(b);
                FileManager.saveRides(rideList);
                FileManager.saveBookings(bookings);
                return true;
            }
        }
        return false;
    }

    public boolean cancelBooking(int bookingId, User user) {
        Booking toRemove = null;
        for (Booking b : bookings) {
            if (b.getBookingId() == bookingId && b.getUser().getId() == user.getId()) {
                toRemove = b; break;
            }
        }
        if (toRemove == null) return false;
        // restore seats
        Ride ride = toRemove.getRide();
        ride.setSeats(ride.getSeats() + toRemove.getSeatsBooked());
        bookings.remove(toRemove);
        FileManager.saveRides(rideList);
        FileManager.saveBookings(bookings);
        return true;
    }

    public List<Booking> viewMyBookings(User user) {
        List<Booking> res = new ArrayList<>();
        for (Booking b : bookings) if (b.getUser().getId() == user.getId()) res.add(b);
        return res;
    }

    private int nextBookingId() {
        return bookings.stream().map(Booking::getBookingId).max(Comparator.naturalOrder()).orElse(0) + 1;
    }
}
