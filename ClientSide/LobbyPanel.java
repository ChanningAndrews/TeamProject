package ClientSide;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.ActionListener;
import java.util.List;

public class LobbyPanel extends JPanel {

    // Fields
    private EJavaClass playerList; // Placeholder for player list display component
    private JButton startGameButton;
    private JLabel lobbyStatus;

    // Constructor
    public LobbyPanel() {
        // Initialize components
        playerList = new EJavaClass(); // Replace with actual component for displaying players
        startGameButton = new JButton("Start Game");
        lobbyStatus = new JLabel();

        // Add components to panel
        add(playerList);
        add(startGameButton);
        add(lobbyStatus);
    }

    // Methods
    public void displayPlayers(List<String> players) {
        // Logic to display players in the playerList component
        System.out.println("Displaying players...");
        // Implement further logic to update playerList with the list of players
    }

    public void setStartGameButton(boolean enabled) {
        startGameButton.setEnabled(enabled);
    }

    public void setLobbyStatus(String status) {
        lobbyStatus.setText(status);
    }

    public void actionListeners(ActionListener startGameListener) {
        startGameButton.addActionListener(startGameListener);
    }
}

