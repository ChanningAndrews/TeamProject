package ClientSide;

public class InitialControl {

    // Fields
    private GameClient gameClient;
    private InitialPanel initialPanel;

    // Constructor
    public InitialControl(GameClient gameClient, InitialPanel initialPanel) {
        this.gameClient = gameClient;
        this.initialPanel = initialPanel;
    }

    // Methods
    public void handleLoginAction() {
        // Logic to handle the login action
        System.out.println("Handling login action.");
        // Implement further login action handling as needed
    }

    public void handleCreateAccountAction() {
        // Logic to handle the create account action
        System.out.println("Handling create account action.");
        // Implement further create account action handling as needed
    }
}
