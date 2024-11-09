package ClientSide;

import java.util.HashMap;
import java.util.Map;

public class LobbyData {

    // Fields
    private Map<String, Boolean> playerNames; // Stores player names and their ready status
    private boolean isReady;

    // Constructor
    public LobbyData() {
        playerNames = new HashMap<>();
        isReady = false;
    }

    // Methods
    public void addPlayer(String name) {
        playerNames.put(name, false); // Adds a player with default ready status as false
    }

    public void removePlayer(String name) {
        playerNames.remove(name);
    }

    public void setPlayerStatus(String name, boolean ready) {
        if (playerNames.containsKey(name)) {
            playerNames.put(name, ready);
        }
    }

    public boolean areAllPlayersReady() {
        for (Boolean ready : playerNames.values()) {
            if (!ready) {
                return false;
            }
        }
        return true;
    }
}

