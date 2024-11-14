package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HostOrJoinGamePanel extends JPanel {

    // Fields
    private JButton hostButton;
    private JButton joinButton;
    private JTextField roomCodeField;
    private JLabel roomCodeLabel;

    // Constructor
    public HostOrJoinGamePanel() {
        setLayout(new BorderLayout());

        // Initialize components
        hostButton = new JButton("Host Game");
        joinButton = new JButton("Join Game");
        roomCodeField = new JTextField(10);
        roomCodeLabel = new JLabel("Enter Room Code:");

        // Panel for buttons and inputs
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(hostButton);

        JPanel joinPanel = new JPanel(new FlowLayout());
        joinPanel.add(roomCodeLabel);
        joinPanel.add(roomCodeField);
        joinPanel.add(joinButton);

        // Add components to main panel
        add(buttonPanel, BorderLayout.NORTH);
        add(joinPanel, BorderLayout.CENTER);
    }

    // Methods

    // Add listeners for host and join buttons
    public void addHostButtonListener(ActionListener hostListener) {
        hostButton.addActionListener(hostListener);
    }

    public void addJoinButtonListener(ActionListener joinListener) {
        joinButton.addActionListener(joinListener);
    }

    // Get the room code entered by the user
    public String getRoomCode() {
        return roomCodeField.getText().trim();
    }

    // Clear the room code field
    public void clearRoomCode() {
        roomCodeField.setText("");
    }

    // Set the panel to join mode with the room code input enabled
    public void setJoinMode() {
        roomCodeField.setEnabled(true);
        joinButton.setEnabled(true);
    }
}
