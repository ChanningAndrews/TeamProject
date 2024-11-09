package ClientSide;

public class LobbyControl {

    // Fields
    private GameClient gameClient;
    private LobbyPanel lobbyPanel;

    // Constructor
    public LobbyControl(GameClient gameClient, LobbyPanel lobbyPanel) {
        this.gameClient = gameClient;
        this.lobbyPanel = lobbyPanel;
    }

    // Methods
    public void updateLobby(LobbyData lobbyData) {
        // Logic to update the lobby panel with new lobby data
        System.out.println("Updating lobby with new data...");
        // Implement further update logic as needed
    }

    public void handleStartGame() {
        // Logic to handle the start of the game
        System.out.println("Starting the game...");
        // Implement further start game logic as needed
    }

    public void handlePlayerReadyStatus(boolean isReady) {
        // Logic to handle the player’s ready status
        if (isReady) {
            System.out.println("Player is ready.");
        } else {
            System.out.println("Player is not ready.");
        }
        // Implement further ready status handling as needed
    }
}

