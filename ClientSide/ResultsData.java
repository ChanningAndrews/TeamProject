package ClientSide;

import java.util.List;

public class ResultsData {

    // Fields
    private String winnerUsername;
    private List<String> playerRanks;

    // Constructor
    public ResultsData(String winnerUsername, List<String> playerRanks) {
        this.winnerUsername = winnerUsername;
        this.playerRanks = playerRanks;
    }

    // Methods
    public String getWinnerUsername() {
        return winnerUsername;
    }

    public List<String> getPlayerRanks() {
        return playerRanks;
    }

    public void setWinner(String username) {
        this.winnerUsername = username;
    }

    public void setPlayerRanks(List<String> rankings) {
        this.playerRanks = rankings;
    }
}

