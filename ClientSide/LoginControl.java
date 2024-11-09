package ClientSide;

public class LoginControl {

    // Fields
    private GameClient gameClient;
    private LoginPanel loginPanel;
    private String username;
    private String password;

    // Constructor
    public LoginControl(GameClient gameClient, LoginPanel loginPanel) {
        this.gameClient = gameClient;
        this.loginPanel = loginPanel;
    }

    // Methods
    public void handleLoginFail() {
        // Logic to handle login failure
        System.out.println("Login failed. Please check your username and password.");
        loginPanel.displayMessage("Login failed. Please check your username and password.");
    }

    public void handleLoginSuccess() {
        // Logic to handle successful login
        System.out.println("Login successful! Welcome, " + username);
        loginPanel.displayMessage("Login successful! Welcome, " + username);
        // Further actions can be implemented, such as proceeding to the main menu
    }
}

