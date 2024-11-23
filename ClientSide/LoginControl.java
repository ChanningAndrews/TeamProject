package ClientSide;

public class LoginControl {

    // Fields
    private GameClient gameClient;
    private LoginPanel loginPanel;

    // Constructor
    public LoginControl(GameClient gameClient, LoginPanel loginPanel) {
        this.gameClient = gameClient;
        this.loginPanel = loginPanel;

        // Adding action listeners for login actions
        loginPanel.addLoginButtonListener(e -> handleLogin());
    }

    // Methods

    // Handles the login button action
    public void handleLogin() {
        String username = loginPanel.getUsername();
        String password = loginPanel.getPassword();

        if (username.isEmpty() || password.isEmpty()) {
            loginPanel.displayMessage("Please enter both username and password.");
            return;
        }
        /*
        // Attempt to log in via GameClient (assuming GameClient has a login method)
        boolean loginSuccessful = gameClient.login(username, password);

        if (loginSuccessful) {
            handleLoginSuccess();
        } else {
            handleLoginFail();
        }

         */
    }

    // Handle successful login
    public void handleLoginSuccess() {
        System.out.println("Login successful for user: " + loginPanel.getUsername());
        loginPanel.displayMessage("Login successful!");
        // Additional actions after login success, such as navigating to the lobby
    }

    // Handle failed login
    public void handleLoginFail() {
        System.out.println("Login failed for user: " + loginPanel.getUsername());
        loginPanel.displayMessage("Invalid username or password. Please try again.");
    }
}
