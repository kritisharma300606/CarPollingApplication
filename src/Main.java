import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        RideBookingSystem system = new RideBookingSystem();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("=== Car Pooling Application ===");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            String choice = sc.nextLine().trim();
            if (choice.equals("1")) {
                System.out.print("Name: ");
                String name = sc.nextLine().trim();
                System.out.print("Email: ");
                String email = sc.nextLine().trim();
                System.out.print("Password: ");
                String password = sc.nextLine().trim();
                boolean ok = system.signup(name, email, password);
                if (ok) System.out.println("Registration successful");
                else System.out.println("Registration failed: email may already exist");

            } else if (choice.equals("2")) {
                System.out.print("Email: ");
                String email = sc.nextLine().trim();
                System.out.print("Password: ");
                String password = sc.nextLine().trim();
                User user = system.login(email, password);
                if (user == null) {
                    System.out.println("Login failed");
                } else {
                    System.out.println("Welcome, " + user.getName());
                    userMenu(system, sc, user);
                }

            } else if (choice.equals("3")) {
                System.out.println("Goodbye");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
        sc.close();
    }

    private static void userMenu(RideBookingSystem system, Scanner sc, User user) {
        while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Create Ride");
            System.out.println("2. View All Rides");
            System.out.println("3. Search Ride");
            System.out.println("4. Book Ride");
            System.out.println("5. Cancel Booking");
            System.out.println("6. View My Bookings");
            System.out.println("7. Logout");
            System.out.print("Choose: ");
            String c = sc.nextLine().trim();
            if (c.equals("1")) {
                System.out.print("Source: ");
                String src = sc.nextLine().trim();
                System.out.print("Destination: ");
                String dst = sc.nextLine().trim();
                System.out.print("Seats: ");
                int seats = parseIntSafe(sc.nextLine().trim());
                System.out.print("Fare: ");
                double fare = parseDoubleSafe(sc.nextLine().trim());
                system.createRide(src, dst, seats, fare, user);
                System.out.println("Ride created successfully");

            } else if (c.equals("2")) {
                List<Ride> rides = system.showAllRide();
                if (rides.isEmpty()) System.out.println("No rides available");
                else for (Ride r : rides) System.out.println(r);

            } else if (c.equals("3")) {
                System.out.print("Source: ");
                String src = sc.nextLine().trim();
                System.out.print("Destination: ");
                String dst = sc.nextLine().trim();
                System.out.print("Seats needed: ");
                int seats = parseIntSafe(sc.nextLine().trim());
                List<Ride> found = system.searchRide(src, dst, seats);
                if (found.isEmpty()) System.out.println("No matching rides found");
                else for (Ride r : found) System.out.println(r);

            } else if (c.equals("4")) {
                System.out.print("Enter ride id to book: ");
                int rid = parseIntSafe(sc.nextLine().trim());
                System.out.print("Seats to book: ");
                int s = parseIntSafe(sc.nextLine().trim());
                boolean ok = system.bookRide(rid, user, s);
                if (ok) System.out.println("Booking successful");
                else System.out.println("Booking failed: not enough seats or ride not found");

            } else if (c.equals("5")) {
                System.out.print("Enter booking id to cancel: ");
                int bid = parseIntSafe(sc.nextLine().trim());
                boolean ok = system.cancelBooking(bid, user);
                if (ok) System.out.println("Booking cancelled");
                else System.out.println("Cancel failed: booking not found or not yours");

            } else if (c.equals("6")) {
                List<Booking> my = system.viewMyBookings(user);
                if (my.isEmpty()) System.out.println("No bookings found");
                else for (Booking b : my) System.out.println(b);

            } else if (c.equals("7")) {
                System.out.println("Logged out");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }

    private static int parseIntSafe(String s) {
        try { return Integer.parseInt(s); } catch (Exception e) { return 0; }
    }

    private static double parseDoubleSafe(String s) {
        try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
    }
}
