package CoreGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class BoostCollectible extends Collectible {
    private static final String BOOST_COLLECTABLE_IMAGE_PATH = "/assets/boost_collectible.png";


    public BoostCollectible(int x, int y){
        super(x,y);
        this.type = "boost";

        try {
            collectibleImage = ImageIO.read(getClass().getResource(BOOST_COLLECTABLE_IMAGE_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void applyEffects(Player player) {
        player.setXSpeed(player.getXSpeed()+ 5);
    }

    public BufferedImage getImage(){
        return this.collectibleImage;
    }


}
