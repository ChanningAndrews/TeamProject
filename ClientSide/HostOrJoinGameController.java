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
        gameData.setHost(true);
        String roomCode = gameData.generateRoomCode();
        System.out.println("Hosting game with room code: " + roomCode);
        //gameClient.createGame();
    }

    public void handleJoinButtonPressed(String roomCode) {
        gameData.setHost(false);
        gameData.setRoomCode(roomCode);
        System.out.println("Joining game with room code: " + roomCode);
        //gameClient.joinGame(roomCode);
    }

    public void updateRoomCode(String roomCode) {
        gameData.setRoomCode(roomCode);
        System.out.println("Room code updated to: " + roomCode);
    }

    public boolean isHost() {
        return gameData.isHost();
    }

    public String getRoomCode() {
        return gameData.getRoomCode();
    }
}
