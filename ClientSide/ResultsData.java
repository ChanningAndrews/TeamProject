package ClientSide;

import java.util.ArrayList;
import java.util.List;

public class ResultsData {

    // Fields
    private String winnerUsername;
    private List<String[]> playerRanks; // Each String[] represents {"Player Name", "Rank", "Score"}

    // Constructor
    public ResultsData(String winnerUsername, List<String[]> playerRanks) {
        this.winnerUsername = winnerUsername;
        this.playerRanks = new ArrayList<>(playerRanks); // Defensive copy for immutability
    }

    // Methods

    // Get the winner's username
    public String getWinnerUsername() {
        return winnerUsername;
    }

    // Set the winner's username
    public void setWinner(String username) {
        this.winnerUsername = username;
    }

    // Get the list of player rankings
    public List<String[]> getPlayerRanks() {
        return new ArrayList<>(playerRanks); // Defensive copy to prevent external modification
    }

    // Set the list of player rankings
    public void setPlayerRanks(List<String[]> rankings) {
        this.playerRanks = new ArrayList<>(rankings); // Defensive copy for immutability
    }

    // Additional method to add a player rank to the list
    public void addPlayerRank(String playerName, String rank, String score) {
        if (this.playerRanks == null) {
            this.playerRanks = new ArrayList<>();
        }
        this.playerRanks.add(new String[]{playerName, rank, score});
    }

    // Optional: Clear all player ranks and winner information (useful if reusing the object)
    public void clearData() {
        winnerUsername = null;
        if (playerRanks != null) {
            playerRanks.clear();
        }
    }
}
