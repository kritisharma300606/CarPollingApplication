public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    // Constructor
    public User(int id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    // GETTERS
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    // SETTER
    public void setName(String name) {
        this.name = name;
    }

    // toString MUST be inside the class
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    // Convert to CSV
    public String toCSV() {
        return id + "," + name + "," + email + "," + password;
    }

    // Factory from CSV parts
    public static User fromCSV(String line) {
        if (line == null || line.trim().isEmpty()) return null;
        String[] parts = line.split(",");
        if (parts.length < 4) return null;
        try {
            int id = Integer.parseInt(parts[0].trim());
            String name = parts[1].trim();
            String email = parts[2].trim();
            String password = parts[3].trim();
            return new User(id, name, email, password);
        } catch (Exception e) {
            return null;
        }
    }
}
