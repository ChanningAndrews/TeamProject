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

    public boolean userCreateAccount(String username, String password) {
        // Logic to create a new account in the database
        System.out.println("Creating account for user: " + username);
        // Assume the database has a method to add a new user
        return database.addUser(username, password);
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

