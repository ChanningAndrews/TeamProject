package CoreGame;

import java.util.ArrayList;
import java.util.List;

public class GameMap {

    // Fields
    private int mapHeight;
    private int mapWidth;
    private List<Platform> platforms;
    private List<CoreGame.Collectable> collectibles;

    // Constructor
    public GameMap(int mapHeight, int mapWidth) {
        this.mapHeight = mapHeight;
        this.mapWidth = mapWidth;
        this.platforms = new ArrayList<>();
        this.collectibles = new ArrayList<>();
    }

    // Methods
    public void addPlatform(Platform platform) {
        platforms.add(platform);
        System.out.println("Platform added at position: " + platform.getPosition());
    }

    public void addCollectible(Collectible collectible) {
        collectibles.add(collectible);
        System.out.println("Collectible added at position: " + collectible.getPosition());
    }

    public void generateMap() {
        // Logic to generate or reset the map with initial platforms and collectibles
        System.out.println("Generating map with dimensions: " + mapWidth + " x " + mapHeight);
        // Implement map generation logic here
    }

    // Getters for map dimensions
    public int getMapHeight() {
        return mapHeight;
    }

    public int getMapWidth() {
        return mapWidth;
    }
}

