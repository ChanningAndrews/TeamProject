package ServerSide;

import CoreGame.GameState;

import java.util.ArrayList;
import java.util.List;

public class GameServer {

    // Fields
    private CoreGame.GameState gameState;
    private DatabaseFile database;
    private List<Object> connectedClients;

    // Constructor
    public GameServer(GameState gameState, DatabaseFile database) {
        this.gameState = gameState;
        this.database = database;
        this.connectedClients = new ArrayList<>().reversed();
    }

    // Methods
    public boolean userLogin(String username, String password) {
        // Logic to handle user login, checking against the database
        System.out.println("Attempting login for user: " + username);
        // Assume the database has a method to validate credentials
        return database.validateCredentials(username, password);
    }

    // Assuming GameServer has access to DatabaseFile and User classes
    public boolean userCreateAccount(String username, String password) {
        // Create a new User object with initial values for gamesPlayed and gamesWon
        User newUser = new User(username, password, 0, 0);
        return database.addUser(newUser);  // Pass the User object to addUser
    }

    public void startListening(int port) {
        // Logic to start listening for client connections on the specified port
        System.out.println("Server is listening on port: " + port);
        // Implement further server listening logic as needed
    }

    public void handleMessageFromClient(Object message) {
        // Logic to handle messages from connected clients
        System.out.println("Received message from client: " + message.toString());
        // Implement message processing logic based on the type and content of the message
    }
}

