package CoreGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.image.BufferedImage;

public class SinglePlayerTesting extends JPanel implements ActionListener {

    // Constants defining game settings
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    private static final int MAP_HEIGHT = 1000;
    private static final int GRAVITY = 1;
    private static final int JUMP_STRENGTH = -15;
    private static final int MOVE_SPEED = 5;
    private static final int GOAL_SIZE = 30;

    // Character properties
    private int characterWidth = 32;// player specific
    private int characterHeight = 32; //player specific
    private int x = 0;//FRAME_WIDTH / 2 - characterWidth / 2;  player specific
    private int y = 504;//MAP_HEIGHT - characterHeight; player specific
    private int yVelocity = 0;
    private boolean inAir = false;
    private boolean isMoving = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean facingLeft = true;
    private boolean gameWon = false;
    private boolean onPlatform = false;

    private int cameraY = MAP_HEIGHT - FRAME_HEIGHT;

    // Game elements
    private ArrayList<Platform> platforms;
    private Rectangle goal;

    // Character and background images
    private BufferedImage currCharSprite; // player specific
    private BufferedImage platformSprite;
    private BufferedImage mapBackground;
    private int animationCounter = 0; //player specific
    private final int NUM_MOVE_ANIMATIONS = 4;
    private long lastTimestamp;
    private long accumulatedTime;

    //added from Markie's implementation

    private TileMap tileMap;
    private double scaleFactor = 1.5;
    private int panelWidth;
    private int panelHeight;

    private int mapHeight;

    //private ArrayList<Player> players;
    private Player myPlayer;

    public SinglePlayerTesting(GameMap tilemap) {
        System.out.println("Entered the singlePlayerTesting constructor.");

        this.tileMap = tilemap.getTileMap();

        //players.add(new Player())
        myPlayer = new Player(1234);
        myPlayer.setYPos(1824);

        myPlayer.setCharacterWidth((int)(myPlayer.getCharacterWidth() * scaleFactor));
        myPlayer.setCharacterHeight((int)(myPlayer.getCharacterHeight() * scaleFactor));
        characterWidth = (int)(characterWidth * scaleFactor);
        characterHeight = (int)(characterHeight * scaleFactor);

        int tileWidth = (int)(tileMap.getTileWidth() * scaleFactor);
        //System.out.println("New tile width: " + tileWidth);
        int tileHeight = (int)(tileMap.getTileHeight() * scaleFactor);
        //System.out.println("New tile height: " + tileHeight);

        this.panelWidth = tileMap.getMapWidth() * tileWidth;
        //System.out.println("panel width: " + panelWidth);

        this.mapHeight = tileMap.getMapHeight() * tileHeight;


        panelHeight = /*tileMap.getMapHeight()*/26 * tileHeight;

        this.cameraY = mapHeight - panelHeight;

        //System.out.println("panel height: " + panelHeight);

        setPreferredSize(new Dimension(panelWidth, panelHeight));

        tileMap.setTileWidth(tileWidth);
        tileMap.setTileHeight(tileHeight);

        System.out.println("Progress check 1");

        // Load character and background images
        try {
            currCharSprite = ImageIO.read(getClass().getResource("/assets/bear_idle.png"));
            myPlayer.updateCurrentPlayerSprite(currCharSprite);
            System.out.println("Successfully read the character sprite.");
            //mapBackground = ImageIO.read(getClass().getResource("/sky_background.png"));
            //platformSprite = ImageIO.read(getClass().getResource("/platform.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Initialize platforms and goal
        platforms = new ArrayList<Platform>();
        generatePlatforms(900, 140, 1848, 75 , 66, 15, platforms );
//        platforms.add(new Platform(150, 480/*MAP_HEIGHT - 100*/, scaleFactor));
//        platforms.add(new Platform(200, 320/*MAP_HEIGHT - 180*/, scaleFactor));
        // More platforms added here...
        //goal = new Rectangle(150, MAP_HEIGHT - 950, GOAL_SIZE, GOAL_SIZE);

        // Start timer for game updates
        Timer timer = new Timer(20, this);
        timer.start();

        lastTimestamp = System.currentTimeMillis();
        accumulatedTime = 0;

        // Set up key listeners for character movement
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    myPlayer.setMoving(true);
                    //isMoving = true;
                    myPlayer.setMovingLeft(true);
                    //movingLeft = true;
                    myPlayer.setFacingLeft(true);
                    //facingLeft = true;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    myPlayer.setMoving(true);
                    myPlayer.setMovingRight(true);
                    myPlayer.setFacingLeft(false);
                    //isMoving = true;
                    //movingRight = true;
                    //facingLeft = false;
                } else if (e.getKeyCode() == KeyEvent.VK_SPACE && !myPlayer.isInTheAir()/*inAir*/) {
                    myPlayer.setMoving(false);//?
                    myPlayer.setYSpeed(JUMP_STRENGTH); // Should have the jump strength be a field in the player class itself.
                    myPlayer.setInAir(true);
                    myPlayer.setOnPlatform(false);
                    //isMoving = false;
                    //yVelocity = JUMP_STRENGTH;
                    //inAir = true;
                    //onPlatform = false;
                }
                repaint(); //redraw the frame every time an input is made.
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    myPlayer.setMoving(false);
                    myPlayer.setMovingLeft(false);
                    //isMoving = false;
                    //movingLeft = false;
                } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    myPlayer.setMoving(false);
                    myPlayer.setMovingRight(false);
                    //isMoving = false;
                    //movingRight = false;
                }
            }
        });
        setFocusable(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameWon) return;

        long currentTime = System.currentTimeMillis();
        long elapsedTime = currentTime - lastTimestamp;
        accumulatedTime += elapsedTime;
        lastTimestamp = currentTime;

        // Update character animation if moving
        if (myPlayer.isMoving()/*isMoving*/) {
            try {
                currCharSprite = ImageIO.read(getClass().getResource("/assets/bear_walk.png"));
                myPlayer.updateCurrentPlayerSprite(currCharSprite.getSubimage(32 * animationCounter, 0, 32, 32));
            } catch (IOException e1) {
                e1.printStackTrace();
            }

            // Update animation every 70ms
            if (accumulatedTime >= 70) {
                animationCounter++;
                accumulatedTime = 0;
            }
            if (animationCounter >= NUM_MOVE_ANIMATIONS) animationCounter = 0;
        } else {
            // Reset to idle sprite when not moving
            try {
                currCharSprite = ImageIO.read(getClass().getResource("/assets/bear_idle.png"));
                myPlayer.updateCurrentPlayerSprite(currCharSprite);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }

        // Handle horizontal movement
        if (myPlayer.isMovingLeft()/*movingLeft*/) myPlayer.setXPos(myPlayer.getXPos() - myPlayer.getXSpeed());//x -= MOVE_SPEED;
        if (myPlayer.isMovingRight()/*movingRight*/) myPlayer.setXPos(myPlayer.getXPos() + myPlayer.getXSpeed());//x += MOVE_SPEED;

        //this part ensures that the player never moves out of bounds horizontally
        myPlayer.setXPos(Math.max(0, Math.min(myPlayer.getXPos(), this.panelWidth - myPlayer.getCharacterWidth())));
        //x = Math.max(0, Math.min(x, this.panelWidth - characterWidth));

        // Apply gravity for jumping/falling
        if (myPlayer.isInTheAir()/*inAir*/) {
            myPlayer.setYSpeed(myPlayer.getYSpeed() + GRAVITY); //gravity should be outside of the player's control I think. So maybe it's specific to the panel controller
            myPlayer.setYPos(myPlayer.getYPos() + myPlayer.getYSpeed());
            //yVelocity += GRAVITY;
            //y += yVelocity;
        }

        // Check collision with platforms
        //boolean onPlatform = false;
        for (Platform platform : platforms) {
            if (myPlayer.getYPos() + myPlayer.getCharacterHeight() >= platform.getYPos() &&
                    myPlayer.getYPos() + myPlayer.getCharacterHeight() <= platform.getYPos() + myPlayer.getYSpeed() &&
                    myPlayer.getXPos() + myPlayer.getCharacterWidth() >= platform.getXPos() &&
                    myPlayer.getXPos()  /*+ myPlayer.getCharacterWidth()*/ <= platform.getXPos() + platform.getWidth()
            	/*y + characterHeight >= platform.getYPos() &&
                //y + characterHeight <= platform.getYPos() + yVelocity &&
                //x + characterWidth > platform.getXPos() &&
                //x < platform.getXPos() + platform.getWidth()*/) {

                myPlayer.setYPos(platform.getYPos() - myPlayer.getCharacterHeight());
                //y = platform.getYPos() - characterHeight;

                myPlayer.setYSpeed(0);
                myPlayer.setInAir(false);
                myPlayer.setOnPlatform(true);
                break;
                //yVelocity = 0;
                //inAir = false;
                //onPlatform = true;
                //break;
            }
            else {
                myPlayer.setOnPlatform(false);
                //onPlatform = false; //to change this logic at some point
            }

        }


        // Check if player is in the air
        if (!myPlayer.isOnPlatform()/*onPlatform*/ && y < /*MAP_HEIGHT*/mapHeight - myPlayer.getCharacterHeight()/*characterHeight*/) {
            myPlayer.setInAir(true);
            //inAir = true;
        }

        // Keep player within map boundaries
        if (myPlayer.getYPos() >= 1824)//y >= 504 /*- characterHeight*/) {
        {
            myPlayer.setYPos(1824);
            myPlayer.setYSpeed(0);
            myPlayer.setInAir(false);

            //y = 504 /*- characterHeight*/;
            //yVelocity = 0;
            //inAir = false;
        }

//        if( y == 520) {
//        	yVelocity = 0;
//        	inAir = false;
//        }

        // Adjust camera position to follow player
        if (myPlayer.getYPos() /*y*/ < cameraY + /*FRAME_HEIGHT*/panelHeight / 3) {
            cameraY = myPlayer.getYPos()/*y*/ - /*FRAME_HEIGHT*/panelHeight / 3;
        } else if (myPlayer.getYPos()/*y*/ > cameraY + /*FRAME_HEIGHT*/panelHeight - myPlayer.getCharacterHeight()/*characterHeight*/) {
            cameraY = myPlayer.getYPos() /*y*/ - /*FRAME_HEIGHT*/panelHeight + myPlayer.getCharacterHeight()/*characterHeight*/;
        }
        cameraY = Math.max(0, Math.min(cameraY, mapHeight - panelHeight));

        // Check if player reached the goal
        //if (new Rectangle(x, y, characterWidth, characterHeight).intersects(goal)) {
        //   gameWon = true;
        //}

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.translate(0, -cameraY);

        if (!gameWon) {
            int[][] mapMatrix = this.tileMap.getMapMatrix();

            for (int row = 0; row < mapMatrix.length; row++) {
                for (int col = 0; col < mapMatrix[row].length; col++) {
                    int tileId = mapMatrix[row][col];
                    Tile tile = tileMap.getTile(tileId);
                    if (tile != null) {

                        g2d.drawImage(tile.getScaledImage(scaleFactor), col * tileMap.getTileWidth(), row * tileMap.getTileHeight(), null);
                    }
                }
            }

            // Draw background, platforms, goal, and player character
            //g2d.drawImage(mapBackground, 0, cameraY, 400, 300, null);
            for (Platform platform : platforms) {
                g2d.drawImage(Platform.getPlatformImage(), platform.getXPos(), platform.getYPos(), platform.getWidth(), platform.getHeight(), null);
            }
            //g2d.setColor(Color.GREEN);
            //g2d.fillOval(goal.x, goal.y, GOAL_SIZE, GOAL_SIZE);
            if (myPlayer.isFacingLeft()) {
                g2d.drawImage(myPlayer.getCurrentPlayerSprite(), myPlayer.getXPos()/*x*/, myPlayer.getYPos()/*y*/, myPlayer.getCharacterWidth(), myPlayer.getCharacterHeight(), null);
            } else {
                g2d.drawImage(myPlayer.getCurrentPlayerSprite(), myPlayer.getXPos() + myPlayer.getCharacterWidth(), myPlayer.getYPos(), -myPlayer.getCharacterWidth(), myPlayer.getCharacterHeight(), null);
            }

            //display the character position and other information for debugging purposes.
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 12));
            g2d.drawString("Avatar Position:", 10, cameraY+10);
            g2d.drawString("( " + myPlayer.getXPos() + " , "+ myPlayer.getYPos() + " )", 10, cameraY+25);
            g2d.drawString("In Air: " + myPlayer.isInTheAir(), 10, cameraY + 40);
            g2d.drawString("On Platform: " + myPlayer.isOnPlatform(), 10, cameraY + 55);
            if(myPlayer.isFacingLeft()) {
                g2d.drawString("Facing: Left" , 10, cameraY + 70);
            }
            else
            {
                g2d.drawString("Facing: Right", 10, cameraY + 70);
            }

            if(myPlayer.isOnPlatform()) {
                //g2d.drawString("Platform Dimensions:", 10, 85);
                g2d.drawString("( " + platforms.get(0).getXPos()+ ", " + platforms.get(0).getYPos() +  " )", 10, cameraY + 85);
                g2d.drawString("( " + platforms.get(0).getWidth()+ ", " + platforms.get(0).getHeight() +  " )", 10, cameraY + 100);
            }

        } else {
            // Display win message
            g2d.setColor(Color.BLACK);
            g2d.setFont(new Font("Arial", Font.BOLD, 24));
            g2d.drawString("You Win!", FRAME_WIDTH / 2 - 50, cameraY + FRAME_HEIGHT / 2);
        }
    }

    //should be incorporated on the server side. Should only be called once to get the platform position
    public void generatePlatforms(int mapWidth, int platformXStartPos, int playerStartingYPos, int baseJumpVal, int platformWidth, int platformHeight, ArrayList<Platform> platformsContainer) {
        int rowPos = playerStartingYPos - baseJumpVal;

        int horizontalGap = 120;
        int currPlatformXPos = platformXStartPos;

        int numPlatformsPerRow = mapWidth / (platformWidth + horizontalGap);

        boolean startDecider = true;

        while(rowPos - platformHeight > 0) {
            for(int i = 0; i < numPlatformsPerRow; i++) {
                platformsContainer.add(new Platform(currPlatformXPos, rowPos, scaleFactor));
                currPlatformXPos += (platformWidth +horizontalGap);
            }

            rowPos -= baseJumpVal;
            startDecider = !startDecider;

            if(startDecider) {
                currPlatformXPos = platformXStartPos;
                numPlatformsPerRow += 1;
            }
            else {
                currPlatformXPos = platformXStartPos + 50;
                numPlatformsPerRow--;
            }

        }
    }


    public static void main(String[] args) throws Exception {
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

        try {

            SinglePlayerTesting gamePanel = new SinglePlayerTesting(gameMap);

            JFrame frame = new JFrame("Game Panel");
            frame.setResizable(false);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(gamePanel);
            frame.pack();
            frame.setVisible(true);
            frame.setLocationRelativeTo(null);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to load the tileset.");
        }

        // Attempt to play background sound (error handling included)
        try {
            SoundPlayer audioPlayer = new SoundPlayer();
            audioPlayer.play();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }




}
