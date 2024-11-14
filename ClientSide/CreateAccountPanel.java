package ClientSide;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreateAccountPanel extends JPanel {

    // Fields
    private JComboBox<String> avatarSelection;  // Example dropdown for avatar selection
    private JLabel creationStatus;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JButton createAccountButton;

    // Constructor
    public CreateAccountPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Initialize components
        avatarSelection = new JComboBox<>(new String[]{"Avatar 1", "Avatar 2", "Avatar 3"}); // Placeholder avatars
        creationStatus = new JLabel("", SwingConstants.CENTER);
        creationStatus.setForeground(Color.RED);

        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        confirmPasswordField = new JPasswordField(15);
        createAccountButton = new JButton("Create Account");

        // Layout components
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Username:"), gbc);

        gbc.gridx = 1;
        add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Password:"), gbc);

        gbc.gridx = 1;
        add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Confirm Password:"), gbc);

        gbc.gridx = 1;
        add(confirmPasswordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Choose Avatar:"), gbc);

        gbc.gridx = 1;
        add(avatarSelection, gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        add(createAccountButton, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        add(creationStatus, gbc);
    }

    // Method to set action listener for create account button
    public void setCreateAccountAction(ActionListener createAccountAction) {
        createAccountButton.addActionListener(createAccountAction);
    }

    // Clear input fields
    public void clearFields() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        creationStatus.setText("");
    }

    // Display a message in the status label
    public void displayMessage(String message, boolean isError) {
        creationStatus.setText(message);
        creationStatus.setForeground(isError ? Color.RED : Color.GREEN);
    }

    // Retrieve user details
    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    public String getSelectedAvatar() {
        return (String) avatarSelection.getSelectedItem();
    }

    // Validate input fields
    public boolean validateFields() {
        String password = getPassword();
        String confirmPassword = getConfirmPassword();

        if (getUsername().isEmpty()) {
            displayMessage("Username cannot be empty.", true);
            return false;
        }
        if (password.isEmpty()) {
            displayMessage("Password cannot be empty.", true);
            return false;
        }
        if (!password.equals(confirmPassword)) {
            displayMessage("Passwords do not match.", true);
            return false;
        }
        return true;
    }
}
