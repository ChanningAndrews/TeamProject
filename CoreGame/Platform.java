package CoreGame;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Platform {
    private int xPos, yPos; // Position of the platform
    private int width = 48, height = 10; // Dimensions of the platform

    private static final String PLATFORM_IMAGE_PATH = "assets/platform.png";
    private static BufferedImage platformImage = null;
    private static boolean imageRead = false;

    public Platform(int xPos, int yPos, double scaleFactor) {
        if (!imageRead) {
            try {
                BufferedImage tmpImage = ImageIO.read(getClass().getResource("/assets/platform.png"));
                platformImage = tmpImage;
                imageRead = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        this.xPos = xPos;
        this.yPos = yPos;
        this.width = (int) (width * scaleFactor);
        this.height = (int) (height * scaleFactor);
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public int getXPos() {
        return this.xPos;
    }

    public int getYPos() {
        return this.yPos;
    }

    public static BufferedImage getPlatformImage() {
        return platformImage;
    }

    // Render method for drawing the platform
    public void render(Graphics g) {
        if (platformImage != null) {
            g.drawImage(platformImage, xPos, yPos, width, height, null);
        }
    }
}
