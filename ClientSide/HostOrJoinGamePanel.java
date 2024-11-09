package ClientSide;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HostOrJoinGamePanel extends JPanel {

    // Fields
    private JButton hostButton;
    private JButton joinButton;
    private JTextField roomCodeField;

    // Constructor
    public HostOrJoinGamePanel() {
        // Initialize components
        hostButton = new JButton("Host Game");
        joinButton = new JButton("Join Game");
        roomCodeField = new JTextField(10);

        // Add components to panel
        add(hostButton);
        add(joinButton);
        add(roomCodeField);

        // Set up button actions
        hostButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle host button action
                System.out.println("Host button clicked.");
                // Further host button logic as needed
            }
        });

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle join button action
                System.out.println("Join button clicked with room code: " + roomCodeField.getText());
                // Further join button logic as needed
            }
        });
    }

    // Methods
    public void displayRoomCode(String code) {
        // Display the room code in the roomCodeField
        roomCodeField.setText(code);
    }

    public void clearRoomCode() {
        // Clear the room code field
        roomCodeField.setText("");
    }

    public void setJoinMode() {
        // Set the panel to "join mode" by enabling/disabling components as needed
        roomCodeField.setEditable(true);
        joinButton.setEnabled(true);
        hostButton.setEnabled(false);
        System.out.println("Join mode activated.");
    }
}

