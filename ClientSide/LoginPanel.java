package ClientSide;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    // Fields
    private JTextField usernameField;
    private JTextField passwordField;
    private JButton loginButton;
    private JLabel loginStatus;

    // Constructor
    public LoginPanel() {
        // Initialize components
        usernameField = new JTextField(15);
        passwordField = new JTextField(15);
        loginButton = new JButton("Login");
        loginStatus = new JLabel();

        // Add components to panel
        add(usernameField);
        add(passwordField);
        add(loginButton);
        add(loginStatus);

        // Set up button action
        loginButton.addActionListener(new ActionListener() {
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
    }

    public void handleSubmitButton(String username, String password) {
        // Handle the login logic here
        if (username.isEmpty() || password.isEmpty()) {
            loginStatus.setText("Please enter both username and password.");
        } else {
            loginStatus.setText("Attempting login...");
            // Implement further login processing as needed
        }
    }

    public void displayPanel() {
        // This method could be used to reset or update the panel display if needed
        loginStatus.setText("");
        clearFields();
    }
}

