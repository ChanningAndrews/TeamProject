package ClientSide;

import CoreGame.GameMap;
import CoreGame.SinglePlayerTesting;

import javax.swing.*;
import java.awt.*;

public class GameGUI {

    // Fields
    private JFrame mainFrame;
    private CardLayout cardLayout;
    private JPanel currentPanel;
    private JPanel loginPanel;
    private JPanel lobbyPanel;
    private JPanel gamePanel;
    private JPanel mainMenuPanel;

    // Constructor
    public GameGUI() throws Exception {
        mainFrame = new JFrame("Game GUI");
        cardLayout = new CardLayout();
        currentPanel = new JPanel(cardLayout);
        mainFrame.add(currentPanel);

        // Initialize panels
        initializeMainMenuPanel();
        initializeLoginPanel();
        initializeLobbyPanel();
        initializeGamePanel();

        // Frame settings
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);  // Set frame size
        mainFrame.setVisible(true);
    }

    // Initialize main menu panel
    private void initializeMainMenuPanel() {
        mainMenuPanel = new JPanel();
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(e -> showLoginPanel());
        mainMenuPanel.add(startButton);
        addPanel(mainMenuPanel, "mainMenu");
    }

    // Initialize login panel
    private void initializeLoginPanel() {
        loginPanel = new JPanel();
        loginPanel.add(new JLabel("Login Screen"));
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> showLobbyPanel());
        loginPanel.add(loginButton);
        addPanel(loginPanel, "login");
    }

    // Initialize lobby panel
    private void initializeLobbyPanel() {
        lobbyPanel = new JPanel();
        lobbyPanel.add(new JLabel("Lobby Screen"));
        JButton startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> showGamePanel());
        lobbyPanel.add(startGameButton);
        addPanel(lobbyPanel, "lobby");
    }

    // Initialize game panel
    private void initializeGamePanel() throws Exception {
        // Set up the game map with a sample map matrix
        int[][] mapMatrix = {
                // Populate with map layout data as needed
        };
        GameMap gameMap = new GameMap(1000, 800, mapMatrix); // Customize map dimensions as needed

        // Instantiate SinglePlayerTesting with GameMap
        SinglePlayerTesting singlePlayerTest = new SinglePlayerTesting(gameMap);

        // Create gamePanel and add singlePlayerTest
        gamePanel = new JPanel(new BorderLayout());
        gamePanel.add(singlePlayerTest, BorderLayout.CENTER);

        addPanel(gamePanel, "game");
    }

    // Methods to switch panels
    public void switchPanel(String panelName) {
        cardLayout.show(currentPanel, panelName);
    }

    public void addPanel(JPanel panel, String name) {
        currentPanel.add(panel, name);
    }

    public void showMainMenu() {
        switchPanel("mainMenu");
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
