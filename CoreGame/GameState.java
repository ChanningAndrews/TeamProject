package CoreGame;

import java.util.ArrayList;
import java.util.List;

public class GameState {

    // Fields
    private List<Player> players;
    private List<Platform> platforms;
    private List<CoreGame.Collectable> collectibles;
    private String currentPlayers;
    private boolean gameInProgress;

    // Constructor
    public GameState() {
        players = new ArrayList<>();
        platforms = new ArrayList<>();
        collectibles = new ArrayList<>();
        currentPlayers = "";
        gameInProgress = false;
    }

    // Methods
    public void addPlayer(Player player) {
        players.add(player);
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void updatePlatform(Platform platform) {
        // Logic to update the specified platform's state
        System.out.println("Updating platform: " + platform);
        // Implement platform update logic as needed
    }

    public void updateCollectible(CoreGame.Collectable collectible) {
        // Logic to update the specified collectible's state
        System.out.println("Updating collectible: " + collectible);
        // Implement collectible update logic as needed
    }

    public void resetGameState() {
        players.clear();
        platforms.clear();
        collectibles.clear();
        currentPlayers = "";
        gameInProgress = false;
        System.out.println("Game state has been reset.");
    }

    // Getters and Setters for currentPlayers and gameInProgress
    public String getCurrentPlayers() {
        return currentPlayers;
    }

    public void setCurrentPlayers(String currentPlayers) {
        this.currentPlayers = currentPlayers;
    }

    public boolean isGameInProgress() {
        return gameInProgress;
    }

    public void setGameInProgress(boolean gameInProgress) {
        this.gameInProgress = gameInProgress;
    }
}

