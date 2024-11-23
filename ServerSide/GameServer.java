package ServerSide;

import CoreGame.*;

import ocsf.server.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;

public class GameServer extends AbstractServer {
    //private TileMap tileMap; // Map data
    HashMap<Integer, Player> players;

    private boolean spikesVisible = false;
    private static final int SPIKE_TOGGLE_INTERVAL = 2000; // Time in milliseconds
    private Timer spikeTimer;

    private ArrayList<Collectible> collectibles;
    private ArrayList<Platform> platforms;
    private ArrayList<Obstacle> spikes;


    public GameServer(int port) {
        super(port);
        setTimeout(1000);
        startSpikeTimer();

        players = new HashMap();
        collectibles = new ArrayList<>();
        platforms = new ArrayList<>();
        spikes = new ArrayList<>();

        generatePlatformsAndTraps(900, 216, 1848, 80 , 66, 15, platforms );

        /*

        // Initialize the map on the server side
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
                // Add more rows as needed
        };
        try {
            this.tileMap = new TileMap("tileset.png", mapMatrix);
        } catch (Exception e) {
            System.out.println("Failed to initialize the map.");
            e.printStackTrace();
        }

        */

    }

    /*
    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
    	try {
            if (msg.equals("REQUEST_MAP")) {
                // Send the map to the client
                client.sendToClient(tileMap);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    	try {
            if (msg instanceof Player) {
            	Player tempPlayer = (Player)msg;
                players.put(tempPlayer.getId(), tempPlayer);
                sendUpdateToClients();
            }
        } catch (Exception e) {
            System.err.println("Error handling message from client: " + e.getMessage());
        }
    }
    */

    @Override
    protected void clientConnected(ConnectionToClient client) {
        //System.out.println("A new client has connected: " + client.getInetAddress().getHostAddress());

        //System.out.println(client);
        //System.out.println(client.getId());

        client.getInfo("");

        String platformString;
        for (Platform platform : platforms){
            platformString = platform.toString();
            try {
                client.sendToClient(platformString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String spikeString;
        for (Obstacle spike : spikes){
            spikeString = spike.toString();
            try {
                client.sendToClient(spikeString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String collectibleString;
        for (Collectible collectible : collectibles){
            collectibleString = collectible.toString();
            try {
                client.sendToClient(collectibleString);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public void serverStarted() {
        System.out.println("Server started");
    }

    private void sendUpdateToClients() {
        synchronized (players) {
            sendToAllClients(players);
        }
    }


    @Override
    protected void handleMessageFromClient(Object msg, ConnectionToClient client) {


        //System.out.println("got something");

        if (msg instanceof String) {
            //System.out.println("got a string");
            String message = (String)msg;

            if(message.startsWith("PlayerId")) {
                //System.out.println("got a player string");
                Player tempPlayer = fromString(message);
                //System.out.println("Temp player created from string: " + tempPlayer);
                if(players.containsKey(tempPlayer.getId())) {
                    System.out.println("Client " + client.getId() + " moved");
                    System.out.println("Client " + client.getId() + " new postions: " + tempPlayer.getPos());
                    for (Thread clientThread : getClientConnections()) {
                        ConnectionToClient tempClient = (ConnectionToClient) clientThread;

                        // Skip the sender
                        if (tempClient != client) {
                            try {
                                tempClient.sendToClient((String)msg);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } // Send the message to other clients
                        }

                    }
                } else {
                    Player newPlayer = fromString(message);
                    players.put(newPlayer.getId(), newPlayer);
                    System.out.println("New player added: " + newPlayer);
                    //System.out.println("Number of players: " + players.size());
                    System.out.println("Player iD: " + newPlayer.getId());
                    //System.out.println("Player x pos: " + newPlayer.getXPos());
                    //System.out.println("Player y pos: " + newPlayer.getYPos());
                    //System.out.println("Current sprite of new player: " + newPlayer.getCurrentPlayerSprite());




                    //For sending the new player info about all of the existing players
                    for (Player existingPlayer : players.values()) {
                        if(existingPlayer.getId() != newPlayer.getId()) {
                            try {
                                client.sendToClient(message);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }

                    }


                    //For sending all of the other players info on the new player
                    for (Thread clientThread : getClientConnections()) {
                        ConnectionToClient tempClient = (ConnectionToClient) clientThread;

                        // Skip the sender
                        if (tempClient != client) {
                            try {
                                tempClient.sendToClient(message);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            } // Send the message to other clients
                        }

                    }
                }
            }


        }




        if (msg instanceof Player) {
            Player tempPlayer = (Player)msg;
            //System.out.println("Client " + client.getId() + " moved");
            //System.out.println("Client " + client.getId() + " new postions: " + tempPlayer.getPos());
            if(players.containsKey(tempPlayer.getId())) {
                for (Thread clientThread : getClientConnections()) {
                    ConnectionToClient tempClient = (ConnectionToClient) clientThread;

                    // Skip the sender
                    if (tempClient != client) {
                        try {
                            tempClient.sendToClient((Player)msg);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } // Send the message to other clients
                    }

                }
            } else {
                Player newPlayer = (Player)msg;
                players.put(newPlayer.getId(), newPlayer);
                //System.out.println("New player added" + msg);
                //System.out.println("Number of players: " + players.size());
                //System.out.println("Player iD: " + newPlayer.getId());
                //System.out.println("Player x pos: " + newPlayer.getXPos());
                //System.out.println("Player y pos: " + newPlayer.getYPos());
                //System.out.println("Current sprite of new player: " + newPlayer.getCurrentPlayerSprite());




                //For sending the new player info about all of the existing players
                for (Player existingPlayer : players.values()) {
                    if(existingPlayer.getId() != newPlayer.getId()) {
                        try {
                            client.sendToClient(existingPlayer);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }

                }


                //For sending all of the other players info on the new player
                for (Thread clientThread : getClientConnections()) {
                    ConnectionToClient tempClient = (ConnectionToClient) clientThread;

                    // Skip the sender
                    if (tempClient != client) {
                        try {
                            tempClient.sendToClient(newPlayer);
                        } catch (IOException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        } // Send the message to other clients
                    }

                }
            }


        }


        if(msg.equals("message")) {
            System.out.println("Client " + client.getId() + " moved");
        }
    }




    @Override
    protected void serverStopped() {
        System.out.println("Server Stopped");
    }

    public void listeningException(Throwable exception) {
        System.out.println("Listening Exception Occurred: ");
        System.out.println(exception.getMessage() + "\n");

    }

    @Override
    protected void serverClosed() {
        System.out.println("The server has been completely shut down.");
    }

    public static Player fromString(String playerString) {
        String[] fields = playerString.split(",");
        int id = -1, xPos = 0, yPos = 0, xSpeed = 0, ySpeed = 0, avatarId = 1, characterHeight = 32, characterWidth = 32;
        boolean inAir = false, onPlatform = false, isMoving = false, facingLeft = false, movingLeft = false, movingRight = false;
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

        return player;
    }

    private void startSpikeTimer() {
        spikeTimer = new Timer(true); // Daemon thread
        spikeTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                spikesVisible = !spikesVisible;
                broadcastSpikeState();
            }
        }, 0, SPIKE_TOGGLE_INTERVAL);
    }

    private void broadcastSpikeState() {
        String spikeStateMessage = "SPIKE_STATE:" + spikesVisible;
        // Broadcast this message to all connected clients
        for (Thread clientThread : getClientConnections()) {
            ConnectionToClient tempClient = (ConnectionToClient) clientThread;

            try {
                tempClient.sendToClient(spikeStateMessage);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }






    public void generatePlatformsAndTraps(int mapWidth, int platformXStartPos, int playerStartingYPos, int baseJumpVal, int platformWidth, int platformHeight, ArrayList<Platform> platformsContainer) {
        Platform lastPlatform = new Platform(1080/2, 48, 1.5);

        int rowPos = playerStartingYPos - baseJumpVal;

        int firstTowerWallXPos = 4 * 24;
        int secondTowerWallXPos = (24 - 4) * 24;

        int towerWidth = secondTowerWallXPos - firstTowerWallXPos;

        int horizontalGap = 120;
        int currPlatformXPos = platformXStartPos;

        int numPlatformsPerRow = towerWidth / (platformWidth + horizontalGap);

        boolean startDecider = true;

        Random random = new Random();
        int spikePlacement = 0;

        int spikeDecider = 0;

        boolean hasSpikes = false;

        int collectibleDecider = 2;

        boolean collectibleOnTop = false;

        while(rowPos - platformHeight > lastPlatform.getYPos()) {
            for(int i = 0; i < numPlatformsPerRow; i++) {
                platformsContainer.add(new Platform(currPlatformXPos, rowPos, 1.5));
                spikeDecider = random.nextInt(4);
                //spikeDecider = 4;

                if(spikeDecider == 0){
                    hasSpikes = true;
                }
                else{
                    hasSpikes = false;
                    collectibleDecider = random.nextInt(8);
                }

                if(!hasSpikes && collectibleDecider == 0){
                    collectibles.add(new BoostCollectible(currPlatformXPos + 15, rowPos - 32));
                }

                if(hasSpikes){
                    spikePlacement = random.nextInt(3);
                    //spikePlacement = 1;
                    spikes.add(new Obstacle(currPlatformXPos + (spikePlacement * 24), rowPos-24, "spikes", 1.5));
                }
                else{

                }

                currPlatformXPos += (platformWidth +horizontalGap);
            }

            rowPos -= (48+baseJumpVal-25);
            startDecider = !startDecider;

            if(startDecider) {
                currPlatformXPos = platformXStartPos;
                numPlatformsPerRow += 1;
            }
            else {
                currPlatformXPos = platformXStartPos + platformWidth + 30;
                numPlatformsPerRow--;
            }

            collectibleDecider = -12;

        }

        platforms.add(lastPlatform);

        System.out.println("Server generated collectibles: " + collectibles);
        System.out.println("Server generated platforms: " + platforms);
        System.out.println("Server generated spikes: " + spikes);
    }

    public String serializeGameData() {
        StringBuilder builder = new StringBuilder();

        // Serialize collectibles
        builder.append("COLLECTIBLES:");
        for (Collectible collectible : collectibles) {
            builder.append(serializeCollectible(collectible)).append(",");
        }
        if (!collectibles.isEmpty()) {
            builder.setLength(builder.length() - 1); // Remove trailing comma
        }
        builder.append("|");

        // Serialize platforms
        builder.append("PLATFORMS:");
        for (Platform platform : platforms) {
            builder.append(serializePlatform(platform)).append(",");
        }
        if (!platforms.isEmpty()) {
            builder.setLength(builder.length() - 1); // Remove trailing comma
        }
        builder.append("|");

        // Serialize spikes
        builder.append("SPIKES:");
        for (Obstacle spike : spikes) {
            builder.append(serializeObstacle(spike)).append(",");
        }
        if (!spikes.isEmpty()) {
            builder.setLength(builder.length() - 1); // Remove trailing comma
        }

        return builder.toString();
    }

    // Helper methods for serializing individual objects
    private String serializeCollectible(Collectible collectible) {
        return collectible.getXPos() + "," + collectible.getYPos() + "," + collectible.getType(); // Adjust fields as necessary
    }

    private String serializePlatform(Platform platform) {
        return platform.getXPos() + "," + platform.getYPos(); // Adjust fields as necessary
    }

    private String serializeObstacle(Obstacle obstacle) {
        return obstacle.getXPos() + "," + obstacle.getYPos() + "," + obstacle.getType(); // Adjust fields as necessary
    }






    public static void main(String[] args) {
        int port = 12345; // Change port as needed
        GameServer server = new GameServer(port);


        try {
            server.listen(); // Start the server
            //System.out.println("Server is running on port " + port);
        } catch (IOException e) {
            System.out.println("Failed to start the server.");
            e.printStackTrace();
        }


        try {
            SoundPlayer audioPlayer = new SoundPlayer();
            audioPlayer.play();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }
}
