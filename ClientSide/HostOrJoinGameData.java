package ClientSide;

import java.util.UUID;

public class HostOrJoinGameData {

    // Fields
    private boolean isHost;
    private String roomCode;

    // Constructor
    public HostOrJoinGameData(boolean isHost) {
        this.isHost = isHost;
    }

    // Methods
    public void generateRoomCode() {
        // Generate a random room code
        roomCode = UUID.randomUUID().toString().substring(0, 8); // Generates an 8-character room code
        System.out.println("Generated room code: " + roomCode);
    }

    public void startSession() {
        if (isHost) {
            generateRoomCode();
            System.out.println("Starting session as host with room code: " + roomCode);
            // Additional logic to start the session as host
        } else {
            System.out.println("Cannot start session. This client is not the host.");
        }
    }

    public void joinSession(String roomCode) {
        if (!isHost) {
            this.roomCode = roomCode;
            System.out.println("Joining session with room code: " + roomCode);
            // Additional logic to join the session with the given room code
        } else {
            System.out.println("Cannot join session. This client is the host.");
        }
    }

    // Getter for roomCode
    public String getRoomCode() {
        return roomCode;
    }

    // Getter and setter for isHost
    public boolean isHost() {
        return isHost;
    }

    public void setHost(boolean isHost) {
        this.isHost = isHost;
    }
}

