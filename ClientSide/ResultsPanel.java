package ClientSide;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ResultsPanel extends JPanel {

    // Fields
    private JLabel resultLabel;
    private JTable rankingsTable;
    private JButton exitButton;

    // Constructor
    public ResultsPanel() {
        setLayout(new BorderLayout());

        // Initialize components
        resultLabel = new JLabel("Game Results", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Arial", Font.BOLD, 18));

        // Table for displaying player rankings and scores
        rankingsTable = new JTable(new DefaultTableModel(new Object[]{"Player", "Rank", "Score"}, 0));
        rankingsTable.setEnabled(false);
        rankingsTable.getTableHeader().setReorderingAllowed(false);

        // Exit button to leave the results screen
        exitButton = new JButton("Exit");

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(exitButton);

        // Add components to the main panel
        add(resultLabel, BorderLayout.NORTH);
        add(new JScrollPane(rankingsTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    // Method to add an action listener to the exit button
    public void addExitButtonListener(ActionListener listener) {
        exitButton.addActionListener(listener);
    }

    // Method to display the winner
    public void showWinner(String winner) {
        resultLabel.setText("Winner: " + winner);
    }

    // Method to display player rankings
    public void displayPlayerRankings(List<String[]> rankings) {
        DefaultTableModel model = (DefaultTableModel) rankingsTable.getModel();
        model.setRowCount(0); // Clear existing rows
        for (String[] ranking : rankings) {
            model.addRow(ranking); // Each entry should be in the format {"Player Name", "Rank", "Score"}
        }
    }
}
