package CoreGame;

import ServerSide.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameState {

    // Fields
    private List<Player> players;
    private List<Platform> platforms;
    private List<Collectable> collectibles;
    private String currentPlayers;
    private boolean gameInProgress;
    private boolean gameWon;
    private Platform goalPlatform; // The goal for winning the game

    // Constructor
    public GameState() {
        players = new ArrayList<>();
        platforms = new ArrayList<>();
        collectibles = new ArrayList<>();
        currentPlayers = "";
        gameInProgress = false;
        gameWon = false;
    }

    // Method to gather game data into a map for server communication
    public Map<String, Object> getGameData() {
        Map<String, Object> gameData = new HashMap<>();

        // Add player data
        List<Map<String, Object>> playerDataList = new ArrayList<>();
        for (Player player : players) {
            Map<String, Object> playerData = new HashMap<>();
            playerData.put("username", User.getUsername());
            playerData.put("x", player.getXPos());
            playerData.put("y", player.getYPos());
            playerDataList.add(playerData);
        }
        gameData.put("players", playerDataList);

        // Add collectible data
        List<Map<String, Object>> collectibleDataList = new ArrayList<>();
        for (int i = 0; i < collectibles.size(); i++) {
            Collectable collectible = collectibles.get(i);
            Map<String, Object> collectibleData = new HashMap<>();
            collectibleData.put("id", i);
            collectibleData.put("x", collectible.getXPos());
            collectibleData.put("y", collectible.getYPos());
            collectibleData.put("collected", collectible.isCollected());
            collectibleDataList.add(collectibleData);
        }
        gameData.put("collectibles", collectibleDataList);

        // Add game status information
        gameData.put("gameInProgress", gameInProgress);
        gameData.put("gameWon", gameWon);

        return gameData;
    }

    // Add a player to the game state
    public void addPlayer(Player player) {
        players.add(player);
    }

    // Remove a player from the game state
    public void removePlayer(Player player) {
        players.remove(player);
    }

    // Get a player by username
    public Player getPlayer(String username) {
        return players.stream()
                .filter(player -> User.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }

    // Add a platform to the game state
    public void addPlatform(Platform platform) {
        platforms.add(platform);
    }

    // Add a collectible to the game state
    public void addCollectible(Collectable collectible) {
        collectibles.add(collectible);
    }

    // Set the goal platform for players to reach
    public void setGoalPlatform(Platform goalPlatform) {
        this.goalPlatform = goalPlatform;
    }

    // Update player state based on physics and map constraints
    public void updatePlayerState(Player player) {
        if (player.isInTheAir()) {
            player.setYSpeed(player.getYSpeed() + 1); // Apply gravity
            player.setYPos(player.getYPos() + player.getYSpeed());
        }

        if (player.getYPos() > 504) { // Keep player within map bounds
            player.setYPos(504);
            player.setYSpeed(0);
            player.setInAir(false);
        }
    }

    // Check and handle platform collisions for a player
    public void checkPlatformCollision(Player player) {
        boolean onPlatform = false;
        for (Platform platform : platforms) {
            if (player.getYPos() + player.getCharacterHeight() >= platform.getYPos()
                    && player.getYPos() + player.getCharacterHeight() <= platform.getYPos() + player.getYSpeed()
                    && player.getXPos() + player.getCharacterWidth() > platform.getXPos()
                    && player.getXPos() < platform.getXPos() + platform.getWidth()) {

                player.setYPos(platform.getYPos() - player.getCharacterHeight()); // Align on platform
                player.setYSpeed(0);
                player.setInAir(false);
                onPlatform = true;
                break;
            }
        }
        player.setOnPlatform(onPlatform);
        if (!onPlatform && player.getYPos() < 504 - player.getCharacterHeight()) {
            player.setInAir(true);
        }
    }

    // Check if a player has reached the goal
    public boolean checkGoalReached(Player player) {
        if (goalPlatform != null
                && player.getYPos() + player.getCharacterHeight() >= goalPlatform.getYPos()
                && player.getXPos() + player.getCharacterWidth() > goalPlatform.getXPos()
                && player.getXPos() < goalPlatform.getXPos() + goalPlatform.getWidth()) {
            gameWon = true;
            return true;
        }
        return false;
    }

    // Reset the game state for a new game
    public void resetGameState() {
        players.clear();
        platforms.clear();
        collectibles.clear();
        currentPlayers = "";
        gameInProgress = false;
        gameWon = false;
        System.out.println("Game state has been reset.");
    }

    // Handle collectible collection by player
    public void handleCollectibleCollection(Player player) {
        collectibles.removeIf(collectible -> {
            if (player.getYPos() + player.getCharacterHeight() > collectible.getYPos()
                    && player.getXPos() + player.getCharacterWidth() > collectible.getXPos()
                    && player.getXPos() < collectible.getXPos() + 20) {
                System.out.println(User.getUsername() + " collected a " + collectible.getEffect());
                return true; // Remove collectible if collected
            }
            return false;
        });
    }

    // Getters and Setters
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

    public boolean isGameWon() {
        return gameWon;
    }

    public void setGameWon(boolean gameWon) {
        this.gameWon = gameWon;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Platform> getPlatforms() {
        return platforms;
    }

    public List<Collectable> getCollectibles() {
        return collectibles;
    }
}
