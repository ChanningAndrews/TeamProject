package CoreGame;

public class Platform {

    // Fields
    private int xPos;
    private int yPos;
    private boolean isBreakable;

    // Constructor
    public Platform(int xPos, int yPos, boolean isBreakable) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.isBreakable = isBreakable;
    }

    // Methods
    public boolean isBreakable() {
        return isBreakable;
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

