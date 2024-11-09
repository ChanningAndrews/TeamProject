package ClientSide;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InitialPanel extends JPanel {

    // Fields
    private JButton loginButton;
    private JButton createAccountButton;

    // Constructor
    public InitialPanel() {
        // Initialize buttons
        loginButton = new JButton("Login");
        createAccountButton = new JButton("Create Account");

        // Add buttons to panel
        add(loginButton);
        add(createAccountButton);

        // Set up button actions
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleLoginButton();
            }
        });

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleCreateAccountButton();
            }
        });
    }

    // Methods
    public void displayPanel() {
        // Logic to display the panel or refresh it if needed
        System.out.println("Displaying Initial Panel.");
    }

    public void handleLoginButton() {
        // Logic to handle login button action
        System.out.println("Login button clicked.");
        // Implement further login button handling logic as needed
    }

    public void handleCreateAccountButton() {
        // Logic to handle create account button action
        System.out.println("Create Account button clicked.");
        // Implement further create account button handling logic as needed
    }
}
