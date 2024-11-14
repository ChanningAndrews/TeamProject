package ClientSide;

public class CreateAccountData {

    // Fields
    private String username;
    private String password;

    // Constructor
    public CreateAccountData(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Methods
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isValid() {
        // Check if username and password meet basic validity criteria
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }

    public boolean passwordMatch(String confirmPassword) {
        // Check if the provided confirm password matches the password
        return password != null && password.equals(confirmPassword);
    }

    public void assignAvatar() {
        // Logic to assign an avatar to the user account
        System.out.println("Assigning avatar...");
        // Implement further avatar assignment logic as needed
    }
}
