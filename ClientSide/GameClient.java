package ClientSide;

import java.io.*;
import java.net.Socket;
import java.util.Map;

public class GameClient {

    // Fields
    private String username;
    private String avatar;
    private GameController gameController;
    private CoreGame.GameState gameState;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private boolean connected;
    private GameGUI gameGUI; // Assuming GameGUI instance for managing UI
    private static final String DEMO_USERNAME = "user";
    private static final String DEMO_PASSWORD = "pass";

    // Constructor
    public GameClient(String username, String avatar, String host, int port, GameGUI gameGUI) {
        this.username = username;
        this.avatar = avatar;
        this.gameGUI = gameGUI;
        configureConnection(host, port);
    }

    // Login method for authenticating the user
    public boolean login(String username, String password) {
        if (DEMO_USERNAME.equals(username) && DEMO_PASSWORD.equals(password)) {
            this.username = username;
            System.out.println("Login successful for user: " + username);
            return true;
        } else {
            System.out.println("Login failed for user: " + username);
            return false;
        }
    }

    // Return to lobby method
    public void returnToLobby() {
        System.out.println("Returning to the lobby...");

        // Step 1: Reset game state
        if (gameState != null) {
            gameState.resetGameState();
            System.out.println("Game state reset.");
        }

        // Step 2: Notify the server (optional, if server needs to track player's return to lobby)
        if (connected) {
            try {
                out.writeObject("RETURN_TO_LOBBY");
                out.writeObject(username);
                out.flush();
                System.out.println("Notified server of return to lobby.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Step 3: Update the UI to show the lobby panel
        if (gameGUI != null) {
            gameGUI.showLobbyPanel();
            System.out.println("Switched to lobby panel.");
        }
    }

    // Getters for username and avatar (if needed in other parts of the program)
    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }

    // Configure connection to server
    public void configureConnection(String host, int port) {
        try {
            socket = new Socket(host, port);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());
            connected = true;
            System.out.println("Connected to server at " + host + ":" + port);
            startListening();
        } catch (IOException e) {
            e.printStackTrace();
            connected = false;
        }
    }

    // Close connection to the server
    public void disconnect() {
        try {
            if (socket != null) {
                socket.close();
            }
            if (out != null) {
                out.close();
            }
            if (in != null) {
                in.close();
            }
            connected = false;
            System.out.println("Disconnected from server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Send login data to the server
    public void sendLoginData(String password) {
        if (connected) {
            try {
                out.writeObject("LOGIN");
                out.writeObject(username);
                out.writeObject(password);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Create a new game/lobby
    public void createGame() {
        if (connected) {
            try {
                out.writeObject("CREATE_GAME");
                out.flush();
                System.out.println("Requested to create a new game.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Join an existing game/lobby
    public void joinGame(String roomCode) {
        if (connected) {
            try {
                out.writeObject("JOIN_GAME");
                out.writeObject(roomCode);
                out.flush();
                System.out.println("Requested to join game with code: " + roomCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Start game command to the server
    public void startGame() {
        if (connected) {
            try {
                out.writeObject("START_GAME");
                out.flush();
                System.out.println("Requested to start the game.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Send "player ready" status to server
    public void sendPlayerReady() {
        if (connected) {
            try {
                out.writeObject("PLAYER_READY");
                out.writeObject(username);
                out.flush();
                System.out.println(username + " is ready.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Send player input to the server
    public void sendInput(Object input) {
        if (connected) {
            try {
                out.writeObject("INPUT");
                out.writeObject(input);
                out.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Listen for messages from the server
    private void startListening() {
        new Thread(() -> {
            try {
                while (connected) {
                    Object response = in.readObject();
                    handleServerResponse(response);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                disconnect();
            }
        }).start();
    }

    // Handle responses from the server
    private void handleServerResponse(Object response) {
        if (response instanceof String) {
            String message = (String) response;
            System.out.println("Server: " + message);
            // Handle various server messages, e.g., "LOGIN_SUCCESS", "GAME_STARTED", etc.
        } else if (response instanceof CoreGame.GameState) {
            gameState = (CoreGame.GameState) response;
            if (gameController != null) {
                gameController.updateGameState(gameState.getGameData()); // Assuming getGameData() returns a Map<String, Object>
            }

        }
    }
}