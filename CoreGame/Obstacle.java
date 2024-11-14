package CoreGame;

public class Obstacle {

    // Fields
    private int xPos;
    private int yPos;
    private String type;

    // Constructor
    public Obstacle(int xPos, int yPos, String type) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.type = type;
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
}

