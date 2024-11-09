package ClientSide;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.CardLayout;

public class GameGUI {

    // Fields
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel currentPanel;

    // Constructor
    public GameGUI() {
        mainFrame = new JFrame("Game GUI");
        cardLayout = new CardLayout();
        currentPanel = new JPanel(cardLayout);
        mainFrame.add(currentPanel);
    }

    // Methods
    public void switchPanel(String panelName) {
        cardLayout.show(currentPanel, panelName);
    }

    public void addPanel(JPanel panel, String name) {
        currentPanel.add(panel, name);
    }

    public void showLoginPanel() {
        switchPanel("login");
    }

    public void showLobbyPanel() {
        switchPanel("lobby");
    }

    public void showGamePanel() {
        switchPanel("game");
    }
}
