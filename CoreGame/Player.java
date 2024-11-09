package CoreGame;

public class Player {

    // Fields
    private String username;
    private int xPos;
    private int yPos;
    private int speed;
    private String avatar;
    private boolean hasCollectible;

    // Constructor
    public Player(String username, String avatar, int xPos, int yPos, int speed) {
        this.username = username;
        this.avatar = avatar;
        this.xPos = xPos;
        this.yPos = yPos;
        this.speed = speed;
        this.hasCollectible = false;
    }

    // Methods
    public void moveLeft() {
        xPos -= speed;
        System.out.println(username + " moved left to position: (" + xPos + ", " + yPos + ")");
    }

    public void moveRight() {
        xPos += speed;
        System.out.println(username + " moved right to position: (" + xPos + ", " + yPos + ")");
    }

    public void jump() {
        yPos += speed * 2; // Adjust jump height as necessary
        System.out.println(username + " jumped to position: (" + xPos + ", " + yPos + ")");
    }

    public void updatePosition(int newX, int newY) {
        xPos = newX;
        yPos = newY;
        System.out.println(username + " updated position to: (" + xPos + ", " + yPos + ")");
    }

    public void collectItem(CoreGame.Collectable item) {
        hasCollectible = true;
        System.out.println(username + " collected an item: " + item.getName());
        // Implement further logic if necessary for handling collectible effects
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public int getSpeed() {
        return speed;
    }

    public String getAvatar() {
        return avatar;
    }

    public boolean hasCollectible() {
        return hasCollectible;
    }
}

