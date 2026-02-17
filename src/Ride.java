public class Ride {

    private int id;
    private String source;
    private String destination;
    private int seats;
    private double fare;
    private User user;   // driver / owner of ride

    // Constructor (UPDATED)
    public Ride(int id, String source, String destination,
                int seats, double fare, User user) {

        this.id = id;
        this.source = source;
        this.destination = destination;
        this.seats = seats;
        this.fare = fare;
        this.user = user;
    }

    // Convert to CSV: id,source,destination,seats,fare,driverUserId
    public String toCSV() {
        return id + "," + source + "," + destination + "," + seats + "," + fare + "," + user.getId();
    }

    public static Ride fromCSV(String line, java.util.List<User> users) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",");
        if (parts.length < 6) return null;
        try {
            int id = Integer.parseInt(parts[0].trim());
            String source = parts[1].trim();
            String destination = parts[2].trim();
            int seats = Integer.parseInt(parts[3].trim());
            double fare = Double.parseDouble(parts[4].trim());
            int driverId = Integer.parseInt(parts[5].trim());
            User driver = null;
            for (User u : users) {
                if (u.getId() == driverId) { driver = u; break; }
            }
            if (driver == null) return null;
            return new Ride(id, source, destination, seats, fare, driver);
        } catch (Exception e) {
            return null;
        }
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public int getSeats() {
        return seats;
    }

    public double getFare() {
        return fare;
    }

    public User getUser() {
        return user;
    }

    // SETTERS
    public void setSeats(int seats) {
        this.seats = seats;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    // toString
    @Override
    public String toString() {
        return "Ride{" +
                "id=" + id +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", seats=" + seats +
                ", fare=" + fare +
                ", driver=" + user.getName() +
                '}';
    }
}
