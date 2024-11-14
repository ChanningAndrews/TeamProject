package ServerSide;

import java.sql.*;
import java.util.HashMap;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.IOException;

public class DatabaseFile {

    // Fields
    private HashMap<String, User> usersCache; // Cache for loaded users
    private Connection connection;

    // SQL Queries
    private static final String INSERT_USER = "INSERT INTO users (username, password, gamesPlayed, gamesWon) VALUES (?, ?, ?, ?)";
    private static final String SELECT_USER = "SELECT * FROM users WHERE username = ?";
    private static final String SELECT_ALL_USERS = "SELECT * FROM users";
    private static final String UPDATE_USER_STATS = "UPDATE users SET gamesPlayed = ?, gamesWon = ? WHERE username = ?";
    private static final String DELETE_USER = "DELETE FROM users WHERE username = ?";

    // Constructor
    public DatabaseFile() {
        usersCache = new HashMap<>();
        connectToDatabase();
    }

    // Establish connection to the database
    private void connectToDatabase() {
        try {
            Properties properties = new Properties();
            properties.load(new FileInputStream("db.properties"));
            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Database connected successfully.");
        } catch (IOException | SQLException e) {
            System.err.println("Failed to connect to the database: " + e.getMessage());
        }
    }

    // Close database connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addUser(User user) {
        String query = "INSERT INTO users (username, password, gamesPlayed, gamesWon) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPasswordHash());
            stmt.setInt(3, user.getGamesPlayed());
            stmt.setInt(4, user.getGamesWon());
            stmt.executeUpdate();

            usersCache.put(user.getUsername(), user);
            System.out.println("User " + user.getUsername() + " added to the database.");
            return true;  // Return true on successful addition
        } catch (SQLException e) {
            System.err.println("Error adding user: " + e.getMessage());
            return false;  // Return false if there was an error
        }
    }


    // Retrieve a user from the database or cache
    public User getUser(String username) {
        // Check cache first
        if (usersCache.containsKey(username)) {
            return usersCache.get(username);
        }

        // If not in cache, query from database
        try (PreparedStatement stmt = connection.prepareStatement(SELECT_USER)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("gamesPlayed"),
                        rs.getInt("gamesWon")
                );
                usersCache.put(username, user);  // Cache the user
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Remove a user from the database
    public void removeUser(String username) {
        try (PreparedStatement stmt = connection.prepareStatement(DELETE_USER)) {
            stmt.setString(1, username);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                usersCache.remove(username);
                System.out.println("User " + username + " removed from the database.");
            } else {
                System.out.println("User " + username + " not found in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Validate user credentials
    public boolean validateCredentials(String username, String password) {
        User user = getUser(username);
        return user != null && user.getPasswordHash().equals(hashPassword(password));
    }

    // Helper method to hash passwords (e.g., SHA-256)
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    // Load all users from the database into memory (optional, based on use case)
    public void loadAllUsers() {
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_USERS)) {

            while (rs.next()) {
                User user = new User(
                        rs.getString("username"),
                        rs.getString("password"),
                        rs.getInt("gamesPlayed"),
                        rs.getInt("gamesWon")
                );
                usersCache.put(user.getUsername(), user);
            }
            System.out.println("Loaded all users from database.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Save user data to the database
    public void save() {
        for (User user : usersCache.values()) {
            try (PreparedStatement stmt = connection.prepareStatement(UPDATE_USER_STATS)) {
                stmt.setInt(1, user.getGamesPlayed());
                stmt.setInt(2, user.getGamesWon());
                stmt.setString(3, user.getUsername());
                stmt.executeUpdate();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("User data saved to the database.");
    }
}
