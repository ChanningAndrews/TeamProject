package ServerSide;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class DatabaseFile {

    // Fields
    private List<Object> accounts;
    private List<Object> avatars;
    private HashMap<String, Object> users;

    // Constructor
    public DatabaseFile() {
        accounts = new ArrayList<>();
        avatars = new ArrayList<>();
        users = new HashMap<>();
    }

    // Methods
    public void addUser(Object user) {
        // Logic to add a user to accounts and users map
        accounts.add(user);
        // Assuming user has a method getUsername() to get the username
        users.put(((User) user).getUsername(), user);
    }

    public Object getUser(String username) {
        // Retrieve a user by username
        return users.get(username);
    }

    public void load() {
        // Logic to load data from a file or source into accounts, avatars, and users
        System.out.println("Loading data...");
        // Implement file reading or data loading logic here
    }

    public void save() {
        // Logic to save data from accounts, avatars, and users to a file or source
        System.out.println("Saving data...");
        // Implement file writing or data saving logic here
    }

    public boolean validateCredentials(String username, String password) {
        boolean valid = true;
        return valid;
    }
}

