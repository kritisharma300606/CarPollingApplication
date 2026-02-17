import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String DATA_DIR = "data";
    private static final String USERS_FILE = DATA_DIR + File.separator + "users.txt";
    private static final String RIDES_FILE = DATA_DIR + File.separator + "rides.txt";
    private static final String BOOKINGS_FILE = DATA_DIR + File.separator + "bookings.txt";

    private static void ensureDataDir() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
    }

    public static List<User> loadUsers() {
        ensureDataDir();
        List<User> users = new ArrayList<>();
        File f = new File(USERS_FILE);
        if (!f.exists()) return users;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                User u = User.fromCSV(line);
                if (u != null) users.add(u);
            }
        } catch (IOException e) {
            System.out.println("Failed to load users: " + e.getMessage());
        }
        return users;
    }

    public static void saveUsers(List<User> users) {
        ensureDataDir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(USERS_FILE))) {
            for (User u : users) pw.println(u.toCSV());
        } catch (IOException e) {
            System.out.println("Failed to save users: " + e.getMessage());
        }
    }

    public static List<Ride> loadRides(List<User> users) {
        ensureDataDir();
        List<Ride> rides = new ArrayList<>();
        File f = new File(RIDES_FILE);
        if (!f.exists()) return rides;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Ride r = Ride.fromCSV(line, users);
                if (r != null) rides.add(r);
            }
        } catch (IOException e) {
            System.out.println("Failed to load rides: " + e.getMessage());
        }
        return rides;
    }

    public static void saveRides(List<Ride> rides) {
        ensureDataDir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(RIDES_FILE))) {
            for (Ride r : rides) pw.println(r.toCSV());
        } catch (IOException e) {
            System.out.println("Failed to save rides: " + e.getMessage());
        }
    }

    public static List<Booking> loadBookings(List<User> users, List<Ride> rides) {
        ensureDataDir();
        List<Booking> bookings = new ArrayList<>();
        File f = new File(BOOKINGS_FILE);
        if (!f.exists()) return bookings;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                Booking b = Booking.fromCSV(line, users, rides);
                if (b != null) bookings.add(b);
            }
        } catch (IOException e) {
            System.out.println("Failed to load bookings: " + e.getMessage());
        }
        return bookings;
    }

    public static void saveBookings(List<Booking> bookings) {
        ensureDataDir();
        try (PrintWriter pw = new PrintWriter(new FileWriter(BOOKINGS_FILE))) {
            for (Booking b : bookings) pw.println(b.toCSV());
        } catch (IOException e) {
            System.out.println("Failed to save bookings: " + e.getMessage());
        }
    }
}
