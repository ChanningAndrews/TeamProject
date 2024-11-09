package ClientSide;

public class HostOrJoinGameController {

    // Fields
    private GameClient gameClient;
    private HostOrJoinGameData gameData;

    // Constructor
    public HostOrJoinGameController(GameClient gameClient, HostOrJoinGameData gameData) {
        this.gameClient = gameClient;
        this.gameData = gameData;
    }

    // Methods
    public void handleHostButton() {
        // Logic to handle the host button action
        System.out.println("Host button pressed.");
        // Implement further hosting logic as needed
    }

    public void handleJoinButtonPressed(String roomCode) {
        // Logic to handle joining a game with a specific room code
        System.out.println("Join button pressed with room code: " + roomCode);
        // Implement further joining logic as needed
    }

    public void updateRoomCode() {
        // Logic to update the room code if necessary
        System.out.println("Updating room code...");
        // Implement room code update logic as needed
    }
}
