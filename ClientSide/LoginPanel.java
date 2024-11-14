package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    // Fields
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JLabel loginStatus;

    // Constructor
    public LoginPanel() {
        setLayout(new GridLayout(4, 1, 10, 10));

        // Initialize components
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginButton = new JButton("Login");
        loginStatus = new JLabel("", SwingConstants.CENTER);
        loginStatus.setForeground(Color.RED); // Set text color for error messages

        // Add components to panel
        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(loginButton);
        add(loginStatus);
    }

    // Method to add an action listener to the login button
    public void addLoginButtonListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    // Method to display a message to the user (e.g., error or success messages)
    public void displayMessage(String message) {
        loginStatus.setText(message);
    }

    // Getters for username and password input fields
    public String getUsername() {
        return usernameField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword()).trim();
    }

    // Method to clear the input fields and message
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        loginStatus.setText("");
    }
}
