package ClientSide;

import ocsf.client.*;
import CoreGame.*;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class GameClient extends AbstractClient {
    //controllers
    private CreateAccountControl createAccountController;
    private LoginControl loginController;
    private GameController gameController;
    private JPanel container;

    private TileMap tileMap;
    TwoPlayerTesting gamePanel = null;

    ArrayList<Platform> platforms;
    ArrayList<Obstacle> spikes;
    ArrayList<Collectible> collectibles;


    private boolean connectionSetUpOver = false;

    // private data fields for storing GUI controllers
    private LoginControl loginControl;
    private CreateAccountControl createAccountControl;

    private String host;

    public void setLoginControl(LoginControl loginControl)
    {
        this.loginControl = loginControl;
    }
    public void setCreateAccountControl(CreateAccountControl createAccountControl)
    {
        this.createAccountControl = createAccountControl;
    }

    public GameClient(String host, int port) {
        super(host, port);
        this.host = host;
        platforms = new ArrayList<>();
        spikes = new ArrayList<>();
        collectibles = new ArrayList<>();
    }

    public void updateHost(String newHost){
        this.host = newHost;
        super.setHost(newHost);
    }

    public String getCurrentHost(){
        return host;
    }

    @Override
    protected void handleMessageFromServer(Object msg) {

        if(msg instanceof String) {
            String message = (String)msg;

            if (message.equals("START_GAME")){
                System.out.println("Starting game");

                CardLayout cardLayout = (CardLayout)container.getLayout();
                cardLayout.show(container, "7");
            }

            if(message.startsWith("PlayerId")) {
                Player tempPlayer = fromString(message);

                //System.out.println(gamePanel.getPlayer().getId());

                if(tempPlayer.getId() == gameController.getPlayer().getId()/*gamePanel.getPlayer().getId()*/) {
                    return;
                }

                if(gameController.getOtherPlayers().containsKey(tempPlayer.getId()) && tempPlayer.getId() != gameController.getPlayer().getId()) {
                    //System.out.println("Player " + tempPlayer.getId() + " moved");
                    //System.out.println("Their new position is " + tempPlayer.getPos());
                    gameController.updateOtherPlayer(tempPlayer);

                    //gamePanel.repaint();
                }
                else{
                    System.out.println("CLIENT IS CALLING ADD NEW PLAYER");
                    gameController.addNewPlayer(tempPlayer);
                    System.out.println("New Player " + tempPlayer.getId() + " joined the game");

                }
            }



            if(message.startsWith("SPIKE_STATE:")){
                boolean newSpikeState = Boolean.parseBoolean(message.split(":")[1]);
                Obstacle.draw = newSpikeState;

                // Optionally trigger a repaint or other actions if necessary

            }


            if(message.startsWith("Platform")){
                System.out.println("Client got a platform from the server");
                Platform tempPlatform = parsePlatformFromString(message);

                platforms.add(tempPlatform);
            }

            if(message.startsWith("Obstacle")){
                System.out.println("Received a spike from the server");
                Obstacle tempObstacle = parseObstacleFromString(message, 1.5);
                spikes.add(tempObstacle);
            }

            if(message.startsWith("Collectible")){
                System.out.println("Received a collectible from the server");
                Collectible tempCollectible = parseCollectibleFromString(message);
                collectibles.add(tempCollectible);
            }

            if(message.equals("Initialization done")){
                System.out.println("Received all the initialized objects from the server. Attempting to set up the controller");

                this.connectionSetUpOver = true;
            }

        }




        if (msg instanceof TileMap) {
            tileMap = (TileMap) msg;
            //System.out.println("Map received from server: " + tileMap);
            // Render the map in the client GUI

            /*
            // Reload the tileset on the client side
            try {
                tileMap.reloadTiles("tileset.png");
            } catch (Exception e) {
                e.printStackTrace();
                //System.out.println("Failed to reload tileset on client.");
            }

            */

            // Create a frame to display the map
            javax.swing.SwingUtilities.invokeLater(() -> {

                //TwoPlayerTesting gamePanel = null;
                try {
                    //gamePanel = new TwoPlayerTesting(tileMap, this);
                    //System.out.println("Sending new player from client, current sprite is: " + gamePanel.getPlayer().getCurrentPlayerSprite());
                    sendToServer(gamePanel.getPlayer());
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                javax.swing.JFrame frame = new javax.swing.JFrame("Multiplayer Game");
                frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
                frame.add(gamePanel);
                frame.pack();
                frame.setVisible(true);
            });
        }

        if (msg instanceof Player) {
            Player tempPlayer = (Player)msg;
            if(gamePanel.getOtherPlayers().containsKey(tempPlayer.getId()) && tempPlayer != gamePanel.getPlayer()) {
                //System.out.println("Player " + tempPlayer.getId() + " moved");
                //System.out.println("Their new position is " + tempPlayer.getPos());
                //gamePanel.updateOtherPlayer(tempPlayer);

                //gamePanel.repaint();
            }
            else{
                gamePanel.addNewPlayer((Player)msg);
                //System.out.println("New Player " + tempPlayer.getId() + " joined the game");

            }

        }

        if (msg instanceof HashMap) {
            gamePanel.updatePositions((HashMap<Integer, Player>) msg);
        }
    }



    public void requestMap() {
        try {
            sendToServer("REQUEST_MAP");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectionException (Throwable exception) {
        System.out.println("Connection Exception Occurred");
        System.out.println(exception.getMessage());
        exception.printStackTrace();
    }

    public void connectionEstablished() {
        System.out.println("Client Connected");
    }

    public void createMap() throws Exception {
        System.out.println("Entered the CreateMap method");
        int[][] mapMatrix = {
                {0, 0, 0, 0, 0, 23, 15, 20, 20, 20, 20, 17, 20, 20, 20, 19, 15, 20, 20, 20, 15, 18, 20, 16, 17, 20, 20, 19, 18, 20, 20, 16, 15, 20, 15, 20, 20, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 15, 16, 20, 20, 19, 20, 20, 20, 20, 18, 18, 20, 15, 20, 20, 20, 17, 18, 15, 20, 20, 16, 17, 15, 20, 20, 15, 16, 20, 19, 16, 19, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 15, 20, 16, 20, 15, 17, 19, 20, 20, 19, 19, 20, 16, 20, 20, 17, 18, 20, 20, 20, 20, 15, 19, 18, 20, 20, 20, 15, 16, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 15, 20, 20, 20, 20, 17, 17, 18, 20, 20, 19, 18, 20, 20, 20, 19, 18, 20, 20, 20, 15, 20, 19, 15, 20, 15, 20, 20, 16, 16, 20, 17, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 19, 18, 20, 16, 20, 20, 20, 20, 19, 18, 20, 15, 20, 20, 16, 20, 20, 17, 19, 20, 20, 20, 20, 20, 20, 15, 18, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 20, 20, 20, 17, 20, 20, 20, 19, 15, 20, 20, 20, 15, 18, 20, 16, 17, 20, 20, 19, 18, 20, 20, 16, 15, 20, 15, 20, 20, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 15, 16, 20, 20, 19, 20, 20, 20, 20, 18, 18, 20, 15, 20, 20, 20, 17, 18, 15, 20, 20, 16, 17, 15, 20, 20, 15, 16, 20, 19, 16, 19, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 15, 20, 16, 20, 15, 17, 19, 20, 20, 19, 19, 20, 16, 20, 20, 17, 18, 20, 20, 20, 20, 15, 19, 18, 20, 20, 20, 15, 16, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 15, 20, 20, 20, 20, 17, 17, 18, 20, 20, 19, 18, 20, 20, 20, 19, 18, 20, 20, 20, 15, 20, 19, 15, 20, 15, 20, 20, 16, 16, 20, 17, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 19, 18, 20, 16, 20, 20, 20, 20, 19, 18, 20, 15, 20, 20, 16, 20, 20, 17, 19, 20, 20, 20, 20, 20, 20, 15, 18, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 20, 20, 20, 17, 20, 20, 20, 19, 15, 20, 20, 20, 15, 18, 20, 16, 17, 20, 20, 19, 18, 20, 20, 16, 15, 20, 15, 20, 20, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 15, 16, 20, 20, 19, 20, 20, 20, 20, 18, 18, 20, 15, 20, 20, 20, 17, 18, 15, 20, 20, 16, 17, 15, 20, 20, 15, 16, 20, 19, 16, 19, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 15, 20, 16, 20, 15, 17, 19, 20, 20, 19, 19, 20, 16, 20, 20, 17, 18, 20, 20, 20, 20, 15, 19, 18, 20, 20, 20, 15, 16, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 15, 20, 20, 20, 20, 17, 17, 18, 20, 20, 19, 18, 20, 20, 20, 19, 18, 20, 20, 20, 15, 20, 19, 15, 20, 15, 20, 20, 16, 16, 20, 17, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 19, 18, 20, 16, 20, 20, 20, 20, 19, 18, 20, 15, 20, 20, 16, 20, 20, 17, 19, 20, 20, 20, 20, 20, 20, 15, 18, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 20, 20, 20, 17, 20, 20, 20, 19, 15, 20, 20, 20, 15, 18, 20, 16, 17, 20, 20, 19, 18, 20, 20, 16, 15, 20, 15, 20, 20, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 15, 16, 20, 20, 19, 20, 20, 20, 20, 18, 18, 20, 15, 20, 20, 20, 17, 18, 15, 20, 20, 16, 17, 15, 20, 20, 15, 16, 20, 19, 16, 19, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 15, 20, 16, 20, 15, 17, 19, 20, 20, 19, 19, 20, 16, 20, 20, 17, 18, 20, 20, 20, 20, 15, 19, 18, 20, 20, 20, 15, 16, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 15, 20, 20, 20, 20, 17, 17, 18, 20, 20, 19, 18, 20, 20, 20, 19, 18, 20, 20, 20, 15, 20, 19, 15, 20, 15, 20, 20, 16, 16, 20, 17, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 19, 18, 20, 16, 20, 20, 20, 20, 19, 18, 20, 15, 20, 20, 16, 20, 20, 17, 19, 20, 20, 20, 20, 20, 20, 15, 18, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 20, 20, 20, 17, 20, 20, 20, 19, 15, 20, 20, 20, 15, 18, 20, 16, 17, 20, 20, 19, 18, 20, 20, 16, 15, 20, 15, 20, 20, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 15, 16, 20, 20, 19, 20, 20, 20, 20, 18, 18, 20, 15, 20, 20, 20, 17, 18, 15, 20, 20, 16, 17, 15, 20, 20, 15, 16, 20, 19, 16, 19, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 15, 20, 16, 20, 15, 17, 19, 20, 20, 19, 19, 20, 16, 20, 20, 17, 18, 20, 20, 20, 20, 15, 19, 18, 20, 20, 20, 15, 16, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 15, 20, 20, 20, 20, 17, 17, 18, 20, 20, 19, 18, 20, 20, 20, 19, 18, 20, 20, 20, 15, 20, 19, 15, 20, 15, 20, 20, 16, 16, 20, 17, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 20, 15, 19, 18, 20, 16, 20, 20, 20, 20, 19, 18, 20, 15, 20, 20, 16, 20, 20, 17, 19, 20, 20, 20, 20, 20, 20, 15, 18, 17, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 16, 15, 20, 20, 20, 19, 19, 17, 20, 20, 20, 18, 20, 20, 20, 20, 20, 15, 20, 20, 16, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 20, 20, 19, 16, 15, 20, 20, 16, 15, 20, 15, 15, 20, 20, 18, 20, 15, 20, 20, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 20, 20, 20, 16, 16, 17, 20, 18, 18, 20, 20, 20, 20, 19, 18, 20, 19, 16, 16, 15, 20, 20, 20, 16, 15, 17, 20, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 20, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 19, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 20, 20, 19, 16, 15, 20, 20, 16, 15, 20, 15, 15, 20, 20, 18, 20, 15, 20, 20, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 20, 20, 20, 16, 16, 17, 20, 18, 18, 20, 20, 20, 20, 19, 18, 20, 19, 16, 16, 15, 20, 20, 20, 16, 15, 17, 20, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 16, 15, 20, 20, 20, 19, 19, 17, 20, 20, 20, 18, 20, 20, 20, 20, 20, 15, 20, 20, 16, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 20, 20, 20, 20, 16, 15, 20, 20, 18, 18, 20, 15, 20, 16, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 19, 16, 20, 20, 20, 16, 17, 20, 20, 15, 18, 20, 20, 20, 20, 19, 20, 20, 19, 20, 16, 15, 20, 20, 20, 16, 20, 20, 17, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 18, 20, 20, 20, 17, 19, 19, 17, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 20, 20, 20, 20, 20, 15, 16, 20, 20, 20, 17, 20, 16, 20, 15, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 15, 20, 16, 16, 20, 20, 20, 20, 16, 15, 18, 20, 20, 19, 16, 15, 20, 20, 16, 15, 20, 15, 15, 20, 20, 18, 20, 15, 20, 20, 16, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 23, 20, 20, 16, 20, 20, 20, 16, 16, 17, 20, 18, 18, 20, 20, 20, 20, 19, 18, 20, 19, 16, 16, 15, 20, 20, 20, 16, 15, 17, 20, 20, 20, 20, 24, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 21, 19, 15, 20, 20, 17, 15, 15, 20, 16, 20, 16, 18, 18, 15, 19, 20, 16, 15, 16, 20, 20, 20, 19, 20, 18, 15, 17, 20, 20, 16, 16, 15, 18, 22, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 15, 20, 20, 20, 15, 16, 20, 20, 17, 20, 20, 17, 15, 20, 20, 16, 15, 20, 20, 20, 15, 20, 20, 16, 20, 16, 15, 20, 20, 19, 18, 20, 20, 19, 19, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 20, 16, 15, 20, 20, 17, 19, 20, 17, 20, 20, 20, 18, 20, 20, 20, 20, 20, 15, 20, 20, 16, 20, 20, 15, 16, 20, 20, 20, 17, 16, 16, 20, 15, 15, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 15, 19, 19, 16, 16, 20, 20, 20, 20, 16, 15, 18, 18, 19, 19, 16, 15, 17, 17, 16, 15, 16, 15, 15, 15, 18, 18, 16, 15, 19, 16, 16, 20, 20, 15, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 13, 14, 13, 14, 13, 14, 13, 14 ,13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 0, 0, 0, 0, 0},
                {2, 1, 2, 1, 2, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 1, 2, 1, 2, 1},
                {4, 3, 4, 3, 4, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 10, 9, 3, 4, 3, 4, 3},
                {6, 5, 6, 5, 6, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 12, 11, 5, 6, 5, 6, 5},
                {8, 7, 8, 7, 8, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 14, 13, 7, 8, 7, 8, 7}
                // Add more rows as needed
        };

        GameMap gameMap = new GameMap(1000, 800, mapMatrix);

        /*
        try {
            this.tileMap = new TileMap("tileset.png", mapMatrix);
        } catch (Exception e) {
            System.out.println("Failed to initialize the map.");
            e.printStackTrace();
        }

         */

        /*
        try {
            tileMap.reloadTiles("tileset.png");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to reload tileset on client.");
        }

         */

                for(Platform p : platforms){
                    System.out.println("adding a platform from client to gameController");
                    gameController.addPlatform(p);
                }

                System.out.println("All platforms from client copied to the gameController");

                for(Obstacle o : spikes){
                    gameController.addSpike(o);
                }

                System.out.println("All spikes from client copied to the gameController");

                for(Collectible c : collectibles){
                    gameController.addCollectible(c);
                }
            //TwoPlayerTesting gamePanel = null;
            try {
                //gamePanel = new TwoPlayerTesting(gameMap, this);
                //System.out.println("Sending new player from client, current sprite is: " + gamePanel.getPlayer().getCurrentPlayerSprite());
                System.out.println("Sending the new player from client");
                sendToServer(gameController.getPlayer().toString());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            System.out.println("setting up the gameController");
            gameController.setUp(gameMap);

//            javax.swing.JFrame frame = new javax.swing.JFrame("Multiplayer Game");
//            frame.setDefaultCloseOperation(javax.swing.JFrame.EXIT_ON_CLOSE);
//            frame.add(gamePanel);
//            frame.pack();
//            frame.setVisible(true);
    }

    public static Player fromString(String playerString) {
        String[] fields = playerString.split(",");
        int id = -1, xPos = 0, yPos = 0, xSpeed = 0, ySpeed = 0, avatarId = 1, characterHeight = 32, characterWidth = 32;
        boolean inAir = false, onPlatform = false, isMoving = false, facingLeft = false, movingLeft = false, movingRight = false, staggered = false;
        String avatarType = "", animationFilePath = "", PLATFORM_IMAGE_PATH = "/bear_idle.png";

        // Parse fields
        for (String field : fields) {
            String[] keyValue = field.split("=");
            String key = keyValue[0].trim();
            String value = keyValue[1].trim();

            switch (key) {
                case "PlayerId": id = Integer.parseInt(value); break;
                case "xPos": xPos = Integer.parseInt(value); break;
                case "yPos": yPos = Integer.parseInt(value); break;
                case "xSpeed": xSpeed = Integer.parseInt(value); break;
                case "ySpeed": ySpeed = Integer.parseInt(value); break;
                case "avatarId": avatarId = Integer.parseInt(value); break;
                case "characterHeight": characterHeight = Integer.parseInt(value); break;
                case "characterWidth": characterWidth = Integer.parseInt(value); break;
                case "inAir": inAir = Boolean.parseBoolean(value); break;
                case "onPlatform": onPlatform = Boolean.parseBoolean(value); break;
                case "isMoving": isMoving = Boolean.parseBoolean(value); break;
                case "facingLeft": facingLeft = Boolean.parseBoolean(value); break;
                case "movingLeft": movingLeft = Boolean.parseBoolean(value); break;
                case "movingRight": movingRight = Boolean.parseBoolean(value); break;
                case "isStaggered": staggered = Boolean.parseBoolean(value); break;
                // case "avatarType": avatarType = value; break;
                // case "animationFilePath": animationFilePath = value; break;
                // case "PLATFORM_IMAGE_PATH": PLATFORM_IMAGE_PATH = value; break;
            }
        }

        // Create a new Player with the parsed data
        Player player = new Player(avatarId);
        player.setId(id);
        player.setPos(xPos, yPos);
        player.setXSpeed(xSpeed);
        player.setYSpeed(ySpeed);
        player.setPlayerDimensions(characterHeight, characterWidth);
        player.setInAir(inAir);
        player.setOnPlatform(onPlatform);
        player.setMoving(isMoving);
        player.setFacingLeft(facingLeft);
        player.setMovingLeft(movingLeft);
        player.setMovingRight(movingRight);
        player.setStaggered(staggered);

        return player;
    }

    public static Platform parsePlatformFromString(String platformString) {
        if (platformString == null || !platformString.startsWith("Platform[")) {
            throw new IllegalArgumentException("Invalid platform string: " + platformString);
        }

        try {
            // Extract the key-value pairs
            String data = platformString.substring(platformString.indexOf('[') + 1, platformString.indexOf(']'));
            String[] keyValuePairs = data.split(",");

            // Parse individual values
            int xPos = 0, yPos = 0, width = 0, height = 0;
            boolean isDisappearing = false, hasSpikes = false;

            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split("=");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "xPos":
                        xPos = Integer.parseInt(value);
                        break;
                    case "yPos":
                        yPos = Integer.parseInt(value);
                        break;
                    case "width":
                        width = Integer.parseInt(value);
                        break;
                    case "height":
                        height = Integer.parseInt(value);
                        break;
                    case "isDisappearing":
                        isDisappearing = Boolean.parseBoolean(value);
                        break;
                    case "hasSpikes":
                        hasSpikes = Boolean.parseBoolean(value);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected key: " + key);
                }
            }

            // Create and return the Platform object
            Platform platform = new Platform(xPos, yPos, 1.5); // Default scale factor to 1.0
            platform.setWidth(width);
            platform.setHeight(height);
            platform.setIsDisappearing(isDisappearing);
            platform.setHasSpikes(hasSpikes);

            return platform;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse platform string: " + platformString, e);
        }
    }


    public static Obstacle parseObstacleFromString(String obstacleString, double scaleFactor) {
        if (obstacleString == null || !obstacleString.startsWith("Obstacle[")) {
            throw new IllegalArgumentException("Invalid obstacle string: " + obstacleString);
        }

        try {
            // Extract the key-value pairs
            String data = obstacleString.substring(obstacleString.indexOf('[') + 1, obstacleString.indexOf(']'));
            String[] keyValuePairs = data.split(",");

            // Parse individual values
            int xPos = 0, yPos = 0, width = 16, height = 16;
            boolean disappears = false, draw = true;
            long timeBeforeDisappearing = 2500, timeBeforeAppearingAgain = 2500;
            String type = "";

            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split("=");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "xPos":
                        xPos = Integer.parseInt(value);
                        break;
                    case "yPos":
                        yPos = Integer.parseInt(value);
                        break;
                    case "type":
                        type = value;
                        break;
                    case "width":
                        width = Integer.parseInt(value);
                        break;
                    case "height":
                        height = Integer.parseInt(value);
                        break;
                    case "disappears":
                        disappears = Boolean.parseBoolean(value);
                        break;
                    case "draw":
                        draw = Boolean.parseBoolean(value);
                        break;
                    case "timeBeforeDisappearing":
                        timeBeforeDisappearing = Long.parseLong(value);
                        break;
                    case "timeBeforeAppearingAgain":
                        timeBeforeAppearingAgain = Long.parseLong(value);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected key: " + key);
                }
            }

            // Create and return the Obstacle object
            Obstacle obstacle = new Obstacle(xPos, yPos, type, scaleFactor);
            obstacle.setWidth(width);
            obstacle.setHeight(height);
            obstacle.setDisappears(disappears);
            Obstacle.draw = draw;
            Obstacle.timeBeforeDisappearing = timeBeforeDisappearing;
            Obstacle.timeBeforeAppearingAgain = timeBeforeAppearingAgain;

            return obstacle;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse obstacle string: " + obstacleString, e);
        }
    }

    public static Collectible parseCollectibleFromString(String collectibleString) {
        if (collectibleString == null || !collectibleString.startsWith("Collectible[")) {
            throw new IllegalArgumentException("Invalid collectible string: " + collectibleString);
        }

        try {
            // Extract the key-value pairs
            String data = collectibleString.substring(collectibleString.indexOf('[') + 1, collectibleString.indexOf(']'));
            String[] keyValuePairs = data.split(",");

            // Parse individual values
            int xPos = 0, yPos = 0, width = 20, height = 20;
            String type = null;
            boolean collected = false;

            for (String pair : keyValuePairs) {
                String[] keyValue = pair.split("=");
                String key = keyValue[0].trim();
                String value = keyValue[1].trim();

                switch (key) {
                    case "xPos":
                        xPos = Integer.parseInt(value);
                        break;
                    case "yPos":
                        yPos = Integer.parseInt(value);
                        break;
                    case "width":
                        width = Integer.parseInt(value);
                        break;
                    case "height":
                        height = Integer.parseInt(value);
                        break;
                    case "type":
                        type = value.equals("None") ? null : value;
                        break;
                    case "collected":
                        collected = Boolean.parseBoolean(value);
                        break;
                    default:
                        throw new IllegalArgumentException("Unexpected key: " + key);
                }
            }

            Collectible collectible = null;
            // Create and return the Collectible object
            if(type.equals("boost") ){
                collectible = new BoostCollectible(xPos, yPos);
            }

            if(type.equals("freeze")){
                collectible = new FreezeCollectible(xPos, yPos);
            }

            collectible.setWidth(width);
            collectible.setHeight(height);
            collectible.setType(type);
            collectible.setCollected(collected);

            return collectible;
        } catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse collectible string: " + collectibleString, e);
        }
    }



    //client should not have a main. Main only runs on ClientGUI()
    /*
    public static void main(String[] args) {
        GameClient client = new GameClient("10.251.147.172", 12345); // Adjust host/port as needed
        try {
            client.openConnection();
            client.createMap();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
    */


    public void setCreateAccountController(CreateAccountControl createAccountController){
        this.createAccountController = createAccountController;
    }

    public void setLoginController(LoginControl loginController){
        this.loginController = loginController;
    }

    public void setGameController(GameController gameController){
        this.gameController = gameController;
    }

    public boolean isConnectionSetUpOver(){
        return this.connectionSetUpOver;
    }
}
