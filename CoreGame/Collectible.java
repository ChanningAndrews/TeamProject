package CoreGame;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.ImageIcon;

public class Collectible {

    // Fields
    private int xPos;
    private int yPos;
    private int width = 20;  // Default width for collectible
    private int height = 20;// Default height for collectible
    private String type;
    private boolean collected;  // New field to track if the collectible is collected
    protected static String COLLECTABLE_IMAGE_PATH = "";
    //protected static Image collectableImage;
    protected  BufferedImage collectibleImage = null;

    // Constructor
    public Collectible(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.collected = false; // Initialize as not collected
    }

    // Method to get the collectible's position
    public int[] getPosition() {
        return new int[]{xPos, yPos};
    }

    // Method to render the collectible
    public void render(Graphics g) {
        if (!collected) {  // Only render if not collected
            if (collectibleImage != null) {
                g.drawImage(collectibleImage, xPos, yPos, width, height, null);
            } else {
                // Draw a default circle if image loading fails
                g.setColor(java.awt.Color.YELLOW);
                g.fillOval(xPos, yPos, width, height);
            }
        }
    }


    public void setPosition(int x, int y) {
        this.xPos = x;
        this.yPos = y;
    }

    public void setPosition(Platform platform){
        this.xPos = platform.getXPos() + 20;
        this.yPos = platform.getYPos() - 30;
    }

    public int getXPos() {
        return xPos;
    }

    public int getYPos() {
        return yPos;
    }

    // For identifying collectible type or effect name
    public String getType() {
        return type != null ? type : "Collectable";
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    // Set collected status
    public void setCollected(boolean collected) {
        this.collected = collected;
    }

    // Check if collectible is collected
    public boolean isCollected() {
        return collected;
    }

    //apply effect to one player
    public void applyEffects(Player player){

    }

    //apply effects to other players/group of players
    public void applyEffects(ArrayList<Player> players){

    }
}
