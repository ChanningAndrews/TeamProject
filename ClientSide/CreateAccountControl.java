package ClientSide;

public class CreateAccountControl {

    // Fields
    private GameClient gameClient;
    private CreateAccountPanel createAccountPanel;

    // Constructor
    public CreateAccountControl(GameClient gameClient, CreateAccountPanel createAccountPanel) {
        this.gameClient = gameClient;
        this.createAccountPanel = createAccountPanel;
    }

    // Methods
    public void handleAccountCreation(String username, String password, String avatar) {
        // Logic to handle account creation
        if (validateAccountCreation(username, password)) {
            System.out.println("Account created with username: " + username + " and avatar: " + avatar);
            // Implement further logic to store the account or send data to gameClient as needed
        } else {
            System.out.println("Account creation failed due to invalid data.");
            // Display an error message on createAccountPanel if needed
        }
    }

    public boolean validateAccountCreation(String username, String password) {
        // Validation logic for account creation
        return username != null && !username.isEmpty() && password != null && !password.isEmpty();
    }
}

