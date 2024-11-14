package CoreGame;

import javax.swing.*;
import java.awt.*;

public class GameView extends JFrame {

    private SinglePlayerTesting gamePanel;

    public GameView() {
        // Set up JFrame properties
        setTitle("Game View");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true); // Allows the window to be resized
        setLayout(new BorderLayout());

        // Initialize the GameMap with your preferred dimensions and map layout
        int mapHeight = 1000; // Adjust as needed
        int mapWidth = 1000; // Adjust as needed
        int[][] mapMatrix = generateMapMatrix(mapHeight, mapWidth); // Define map matrix or load it

        try {
            GameMap gameMap = new GameMap(mapHeight, mapWidth, mapMatrix);
            gamePanel = new SinglePlayerTesting(gameMap);
            add(gamePanel, BorderLayout.CENTER); // Add game panel to JFrame
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to load game resources.");
        }

        pack();
        setLocationRelativeTo(null); // Center the window
        setVisible(true);
    }

    // Example method to generate a map matrix (adjust this method according to your needs)
    private int[][] generateMapMatrix(int height, int width) {
        // This is a simple example; you may want to load or generate this matrix differently
        int[][] matrix = new int[height / 16][width / 16]; // Assuming 16x16 tile size
        for (int row = 0; row < matrix.length; row++) {
            for (int col = 0; col < matrix[row].length; col++) {
                matrix[row][col] = (row + col) % 2; // Example pattern
            }
        }
        return matrix;
    }


}
