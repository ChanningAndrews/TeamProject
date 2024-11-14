package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class InitialPanel extends JPanel {

    // Fields
    private JButton loginButton;
    private JButton createAccountButton;
    private JButton joinLobbyButton;
    private JLabel titleLabel;

    // Constructor
    public InitialPanel() {
        setLayout(new BorderLayout());

        // Initialize title
        titleLabel = new JLabel("Welcome to the Game", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // Initialize buttons
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");
        joinLobbyButton = new JButton("Join Lobby");

        // Add buttons to a button panel
        JPanel buttonPanel = new JPanel(new GridLayout(3, 1, 10, 10));
        buttonPanel.add(loginButton);
        buttonPanel.add(createAccountButton);
        buttonPanel.add(joinLobbyButton);

        // Add components to the main panel
        add(titleLabel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }

    // Set action listeners for each button
    public void setButtonActions(ActionListener loginListener, ActionListener createAccountListener, ActionListener joinLobbyListener) {
        loginButton.addActionListener(loginListener);
        createAccountButton.addActionListener(createAccountListener);
        joinLobbyButton.addActionListener(joinLobbyListener);
    }

    // Display the panel (for example, when switching views)
    public void displayPanel() {
        System.out.println("Displaying Initial Panel.");
    }
}
