public class Booking {

    private int booking_id;
    private Ride ride;
    private User user;
    private int seats_booked;
    private double total_fare;

    public Booking(int booking_id, Ride ride, User user, int seats_booked) {
        this.booking_id = booking_id;
        this.ride = ride;
        this.user = user;
        this.seats_booked = seats_booked;
        this.total_fare = seats_booked * ride.getFare(); // FIX HERE
    }

    @Override
    public String toString() {
        return "Booking{" +
                "booking_id=" + booking_id +
                ", ride=" + ride +
                ", user=" + user +
                ", seats_booked=" + seats_booked +
                ", total_fare=" + total_fare +
                '}';
    }

    public int getBookingId() { return booking_id; }
    public Ride getRide() { return ride; }
    public User getUser() { return user; }
    public int getSeatsBooked() { return seats_booked; }
    public double getTotalFare() { return total_fare; }

    public String toCSV() {
        return booking_id + "," + ride.getId() + "," + user.getId() + "," + seats_booked + "," + total_fare;
    }

    public static Booking fromCSV(String line, java.util.List<User> users, java.util.List<Ride> rides) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",");
        if (parts.length < 5) return null;
        try {
            int bookingId = Integer.parseInt(parts[0].trim());
            int rideId = Integer.parseInt(parts[1].trim());
            int userId = Integer.parseInt(parts[2].trim());
            int seatsBooked = Integer.parseInt(parts[3].trim());
            double totalFare = Double.parseDouble(parts[4].trim());

            Ride foundRide = null;
            for (Ride r : rides) if (r.getId() == rideId) { foundRide = r; break; }
            User foundUser = null;
            for (User u : users) if (u.getId() == userId) { foundUser = u; break; }
            if (foundRide == null || foundUser == null) return null;

            Booking b = new Booking(bookingId, foundRide, foundUser, seatsBooked);
            b.total_fare = totalFare;
            return b;
        } catch (Exception e) {
            return null;
        }
    }
}
