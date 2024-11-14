package ClientSide;

import java.util.UUID;

public class HostOrJoinGameData {

    // Fields
    private boolean isHost;
    private String roomCode;

    // Constructor
    public HostOrJoinGameData() {
        this.isHost = false;
        this.roomCode = "";
    }

    // Methods

    // Set host status
    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }

    // Check if the player is the host
    public boolean isHost() {
        return isHost;
    }

    // Generate a unique room code for hosting a game
    public String generateRoomCode() {
        this.roomCode = UUID.randomUUID().toString().substring(0, 6).toUpperCase(); // Generates a short unique code
        return roomCode;
    }

    // Get the room code
    public String getRoomCode() {
        return roomCode;
    }

    // Set the room code (used when joining a game)
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }
}
