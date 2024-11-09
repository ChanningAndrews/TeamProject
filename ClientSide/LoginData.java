package ClientSide;

public class LoginData {

    // Fields
    private String username;
    private String password;

    // Methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValid() {
        // Check if username and password are not null or empty
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    public void clear() {
        username = null;
        password = null;
    }
}

