package CoreGame;

public class Collectable {

    // Fields
    private int xPos;
    private int yPos;
    private String effect;

    // Constructor
    public Collectable(int xPos, int yPos, String effect) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.effect = effect;
    }

    // Methods
    public String getEffect() {
        return effect;
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

    public String getName() {
        String fieldname = new String();
        return fieldname;
    }
}

