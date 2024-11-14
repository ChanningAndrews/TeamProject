package ClientSide;

public class ResultsController {

    // Fields
    private GameClient gameClient;
    private ResultsPanel resultsPanel;

    // Constructor
    public ResultsController(GameClient gameClient, ResultsPanel resultsPanel) {
        this.gameClient = gameClient;
        this.resultsPanel = resultsPanel;

        // Adding an action listener for the exit button
        resultsPanel.addExitButtonListener(e -> handleExitButton());
    }

    // Methods

    // Display the results from ResultsData
    public void displayResults(ResultsData resultsData) {
        String winner = resultsData.getWinnerUsername();
        resultsPanel.showWinner(winner);
        resultsPanel.displayPlayerRankings(resultsData.getPlayerRanks());
        System.out.println("Displaying results for the game. Winner: " + winner);
    }

    // Update rankings, e.g., after a match or when new results come in
    public void updateRankings(ResultsData resultsData) {
        resultsPanel.displayPlayerRankings(resultsData.getPlayerRanks());
        System.out.println("Updated player rankings displayed.");
    }

    // Handle the action of exiting the results screen
    public void handleExitButton() {
        System.out.println("Exiting results screen and returning to the main lobby.");
        // Logic to return to the main lobby or main menu
        gameClient.returnToLobby();
    }
}
