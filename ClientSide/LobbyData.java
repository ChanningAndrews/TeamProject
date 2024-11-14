package ClientSide;

import java.util.ArrayList;
import java.util.List;

public class LobbyData {

    // Inner Player class to store each player's name and ready status
    public static class Player {
        private String name;
        private boolean ready;

        public Player(String name) {
            this.name = name;
            this.ready = false;
        }

        public String getUsername() {
            return name;
        }

        public boolean isReady() {
            return ready;
        }

        public void setReady(boolean ready) {
            this.ready = ready;
        }
    }

    // Fields
    private List<Player> players;

    // Constructor
    public LobbyData() {
        players = new ArrayList<>();
    }

    // Methods

    // Add a player to the lobby
    public void addPlayer(String name) {
        players.add(new Player(name));
        System.out.println("Player added to lobby: " + name);
    }

    // Remove a player from the lobby
    public void removePlayer(String name) {
        players.removeIf(player -> player.getUsername().equals(name));
        System.out.println("Player removed from lobby: " + name);
    }

    // Set the ready status of a player by name
    public void setPlayerReadyStatus(String name, boolean ready) {
        players.stream()
                .filter(player -> player.getUsername().equals(name))
                .forEach(player -> player.setReady(ready));
    }

    // Get the list of players
    public List<Player> getPlayers() {
        return players;
    }

    // Check if all players are ready
    public boolean allPlayersReady() {
        return players.stream().allMatch(Player::isReady);
    }
}
