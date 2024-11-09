package ClientSide;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreateAccountPanel extends JPanel {

    // Fields
    private EJavaClass avatarSelection; // Placeholder for avatar selection type
    private JLabel creationStatus;
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton createAccountButton;

    // Constructor
    public CreateAccountPanel() {
        // Initialize components
        avatarSelection = new EJavaClass(); // Replace with actual avatar selection component
        creationStatus = new JLabel();
        usernameField = new JTextField(15);
        passwordField = new JTextField(15);
        createAccountButton = new JButton("Create Account");

        // Add components to panel
        add(avatarSelection);
        add(creationStatus);
        add(usernameField);
        add(passwordField);
        add(createAccountButton);

        // Set up button action
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmitButton(usernameField.getText(), passwordField.getText());
            }
        });
    }

    // Methods
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        creationStatus.setText("");
    }

    public void displayMessage(String message) {
        creationStatus.setText(message);
    }

    public boolean comparePasswords(String password, String confirmPassword) {
        return password != null && password.equals(confirmPassword);
    }

    public void displayPanel() {
        clearFields();
        creationStatus.setText("Please enter your details to create an account.");
    }

    public void handleSubmitButton(String username, String password) {
        // Logic to handle the submit button action
        if (username.isEmpty() || password.isEmpty()) {
            displayMessage("Username and password cannot be empty.");
        } else {
            displayMessage("Account creation in progress...");
            // Implement further account creation logic as needed
        }
    }
}

