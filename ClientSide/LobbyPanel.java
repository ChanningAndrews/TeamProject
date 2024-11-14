package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class LobbyPanel extends JPanel {

    // Fields
    private HashMap<String, JLabel> playerStatusLabels;  // Mapping of player usernames to status labels
    private JButton startGameButton;
    private JLabel lobbyStatus;

    // Constructor
    public LobbyPanel() {
        playerStatusLabels = new HashMap<>();
        startGameButton = new JButton("Start Game");
        lobbyStatus = new JLabel("Waiting for players...");

        // Layout setup
        setLayout(new BorderLayout());
        JPanel playerListPanel = new JPanel(new GridLayout(0, 1)); // Dynamic list of players
        add(playerListPanel, BorderLayout.CENTER);
        add(startGameButton, BorderLayout.SOUTH);
        add(lobbyStatus, BorderLayout.NORTH);
    }

    // Method to update the lobby display with current data
    public void updateLobbyDisplay(LobbyData lobbyData) {
        removeAll();  // Clear existing components

        JPanel playerListPanel = new JPanel(new GridLayout(0, 1));
        for (LobbyData.Player player : lobbyData.getPlayers()) {
            String username = player.getUsername();
            boolean isReady = player.isReady();

            // Update or create a label for each player
            JLabel statusLabel = playerStatusLabels.getOrDefault(username, new JLabel());
            statusLabel.setText(username + (isReady ? ": Ready" : ": Not Ready"));
            statusLabel.setForeground(isReady ? Color.GREEN : Color.RED);

            playerStatusLabels.put(username, statusLabel);  // Update cache
            playerListPanel.add(statusLabel);  // Add label to display
        }

        add(playerListPanel, BorderLayout.CENTER);
        add(startGameButton, BorderLayout.SOUTH);
        add(lobbyStatus, BorderLayout.NORTH);

        revalidate();
        repaint();
        System.out.println("Lobby display updated.");
    }
    public void updateReadyStatus(String username, boolean isReady) {
        JLabel statusLabel = playerStatusLabels.get(username);
        if (statusLabel != null) {
            statusLabel.setText(username + (isReady ? ": Ready" : ": Not Ready"));
            statusLabel.setForeground(isReady ? Color.GREEN : Color.RED);  // Visual indicator
        } else {
            System.out.println("Player not found: " + username);
        }
    }
    public void addPlayer(String username, boolean isReady) {
        if (!playerStatusLabels.containsKey(username)) {
            JLabel statusLabel = new JLabel(username + (isReady ? ": Ready" : ": Not Ready"));
            statusLabel.setForeground(isReady ? Color.GREEN : Color.RED);
            playerStatusLabels.put(username, statusLabel);
            add(statusLabel);  // Add the label to the panel

            revalidate();  // Refresh the layout
            repaint();
        } else {
            System.out.println("Player already exists in the lobby: " + username);
        }
    }
    public void removePlayer(String username) {
        JLabel statusLabel = playerStatusLabels.remove(username);
        if (statusLabel != null) {
            remove(statusLabel);  // Remove the label from the panel

            revalidate();  // Refresh the layout
            repaint();
        } else {
            System.out.println("Player not found: " + username);
        }
    }

    // Other methods like setLobbyStatus, addPlayer, removePlayer, etc.
    public void setLobbyStatus(String status) {
        lobbyStatus.setText(status);
    }

    public void setStartGameButton(boolean enabled) {
        startGameButton.setEnabled(enabled);
    }

    public void addStartGameListener(ActionListener listener) {
        startGameButton.addActionListener(listener);
    }
}
