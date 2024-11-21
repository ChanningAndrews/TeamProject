package CoreGame;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Obstacle {

    // Fields
    private int xPos;
    private int yPos;
    private static String type;
    private int width = 16, height = 16;

    private static BufferedImage hazardImage = null;
    private static boolean imageRead = false;

    private boolean disappears = false;
    public static boolean draw = true;

    public static long timeBeforeDisappearing = 2500;
    public static long timeBeforeAppearingAgain = 2500;

    // Constructor
    public Obstacle(int xPos, int yPos, String type, double scaleFactor) {
        if (!type.equals(Obstacle.type)) {
            try {
                Obstacle.type = type;
                BufferedImage tmpImage = ImageIO.read(getClass().getResource("/assets/" + type + ".png"));
                hazardImage = tmpImage;
                imageRead = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if ((Obstacle.type).equals("spikes")) {
            disappears = true;
        }

        this.xPos = xPos;
        this.yPos = yPos;

        this.width = (int) (this.width * scaleFactor);
        this.height = (int) (this.height * scaleFactor);

    }

    // Methods
    public String getType() {
        return type;
    }

    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    // Getters for position
    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public static BufferedImage getHazardImage() {
        return hazardImage;
    }

    // Render method for drawing the platform
    public void render(Graphics g) {
        if (hazardImage != null) {
            g.drawImage(hazardImage, xPos, yPos, width, height, null);
        }
    }

    // stagger method for when the player collides with an obstacle. should be implemented in the game controller

}

