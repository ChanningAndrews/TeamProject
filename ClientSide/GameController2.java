package ClientSide;

import CoreGame.Collectible;
import CoreGame.GameMap;
import CoreGame.GameView;
import CoreGame.SinglePlayerTesting;

import javax.swing.*;
import java.util.List;
import java.util.Map;

public class GameController2 {

    private GameMap gameMap;
    private GameView gameView;
    private SinglePlayerTesting singlePlayerTesting;

    public GameController2() throws Exception {
        initializeGameMap();   // Initialize the game map first
        initializeGamePanel(); // Initialize the game panel
    }

    private void initializeGameMap() throws Exception {
        int[][] mapMatrix = {
                {0, 0, 0, 0, 0, 23, 16, 15, 20, 20, 20, 19, 19, 17, 20, 20, 20, 18, 20, 20, 20, 20, 20, 15, 20, 20, 16, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 20, 20, 19, 16, 15, 20, 20, 16, 15, 20, 15, 15, 20, 20, 18, 20, 15, 20, 20, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 20, 20, 20, 16, 16, 17, 20, 18, 18, 20, 20, 20, 20, 19, 18, 20, 19, 16, 16, 15, 20, 20, 20, 16, 15, 17, 20, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 20, 20, 19, 16, 15, 20, 20, 16, 15, 20, 15, 15, 20, 20, 18, 20, 15, 20, 20, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 20, 20, 20, 16, 16, 17, 20, 18, 18, 20, 20, 20, 20, 19, 18, 20, 19, 16, 16, 15, 20, 20, 20, 16, 15, 17, 20, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 16, 15, 20, 20, 20, 19, 19, 17, 20, 20, 20, 18, 20, 20, 20, 20, 20, 15, 20, 20, 16, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 20, 20, 19, 16, 15, 20, 20, 16, 15, 20, 15, 15, 20, 20, 18, 20, 15, 20, 20, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 20, 20, 20, 16, 16, 17, 20, 18, 18, 20, 20, 20, 20, 19, 18, 20, 19, 16, 16, 15, 20, 20, 20, 16, 15, 17, 20, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 21, 19, 15, 15, 20, 17, 15, 15, 20, 16, 20, 16, 18, 18, 15, 19, 20, 16, 15, 16, 20, 20, 20, 19, 20, 18, 15, 17, 20, 20, 16, 16, 15, 18, 22, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 15, 20, 20, 20, 15, 16, 20, 20, 17, 20, 20, 17, 15, 20, 20, 16, 15, 20, 20, 20, 15, 20, 20, 16, 20, 16, 15, 20, 20, 19, 18, 20, 20, 19, 19, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 16, 15, 20, 20, 17, 19, 20, 17, 20, 20, 20, 18, 20, 20, 20, 20, 20, 15, 20, 20, 16, 20, 20, 15, 16, 20, 20, 20, 17, 16, 16, 20, 15, 15, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 15, 19, 19, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 17, 17, 16, 15, 16, 15, 15, 15, 18, 18, 16, 15, 19, 16, 16, 20, 20, 15, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 13, 14, 13, 14, 13, 14, 13, 14 ,13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 0, 0, 0, 0, 0},
                {2, 1, 2, 1, 2, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 1, 2, 1, 2, 1},
                {4, 3, 4, 3, 4, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 3, 4, 3, 4, 3},
                {6, 5, 6, 5, 6, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 5, 6, 5, 6, 5},
                {8, 7, 8, 7, 8, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 7, 8, 7, 8, 7}
        };
        gameMap = new GameMap(1000, 800, mapMatrix); // Customize map dimensions
    }

    private void initializeGamePanel() {
        // Pass GameMap to SinglePlayerTesting
        singlePlayerTesting = new SinglePlayerTesting(gameMap);

        // Create GameView with GameMap
        gameView = new GameView();

        JFrame frame = new JFrame("Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(gameView);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
        // Update Game State based on server response
        // In GameController
        public void updateGameState(Map<String, Object> gameState) {
            if (gameState.containsKey("playerPosition")) {
                Map<String, Integer> playerPosition = (Map<String, Integer>) gameState.get("playerPosition");
                int x = playerPosition.getOrDefault("x", gameMap.getPlayer().getXPos());
                int y = playerPosition.getOrDefault("y", gameMap.getPlayer().getYPos());
                gameMap.getPlayer().setPos(x, y);
            }

            if (gameState.containsKey("collectibles")) {
                List<Map<String, Object>> collectiblesData = (List<Map<String, Object>>) gameState.get("collectibles");
                updateCollectibles(collectiblesData);
            }

            if (gameState.containsKey("score")) {
                int score = (Integer) gameState.get("score");
                System.out.println("Updated score: " + score);
            }

            if (gameState.containsKey("gameStatus")) {
                String gameStatus = (String) gameState.get("gameStatus");
                handleGameStatusChange(gameStatus);
            }

            // Refresh the game view
            gameView.repaint();
        }


        // Helper method to update collectibles based on server response
        private void updateCollectibles(List<Map<String, Object>> collectiblesData) {
            List<Collectible> collectibles = gameMap.getCollectibles();
            for (Map<String, Object> data : collectiblesData) {
                int id = (Integer) data.get("id");
                boolean collected = (Boolean) data.get("collected");
                Collectible collectible = collectibles.get(id);
                collectible.setCollected(collected);
            }
        }

        // Helper to handle different game statuses like "win" or "loss"
        private void handleGameStatusChange(String status) {
            if ("win".equals(status)) {
                System.out.println("Game Won!");
                // Handle win state, like displaying a win screen or stopping the game
            } else if ("loss".equals(status)) {
                System.out.println("Game Lost.");
                // Handle loss state, like resetting the game or displaying a loss screen
            }
        }

        // Other methods like handleServerResponse would call updateGameState with server data
    }
