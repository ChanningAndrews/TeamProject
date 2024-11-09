package ClientSide;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultsPanel extends JPanel {

    // Fields
    private JLabel resultLabel;
    private EJavaClass playerRankings; // Placeholder for player rankings display component
    private JButton exitButton;

    // Constructor
    public ResultsPanel() {
        // Initialize components
        resultLabel = new JLabel();
        playerRankings = new EJavaClass(); // Replace with actual component for displaying rankings
        exitButton = new JButton("Exit");

        // Add components to panel
        add(resultLabel);
        add(playerRankings);
        add(exitButton);
    }

    // Methods
    public void showWinner(String winner) {
        resultLabel.setText("Winner: " + winner);
    }

    public void displayPlayerRankings(List<String> rankings) {
        // Logic to display player rankings
        System.out.println("Displaying player rankings...");
        // Implement further logic to update playerRankings component with the list of rankings
    }

    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }
}

