package CoreGame;

import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.ArrayList;

public class FreezeCollectible extends Collectible{
    private static final String FREEZE_COLLECTABLE_IMAGE_PATH = "/assets/boost_collectible.png";


    public FreezeCollectible(int x, int y){

        super(x,y);

        if(!FREEZE_COLLECTABLE_IMAGE_PATH.equals(COLLECTABLE_IMAGE_PATH)){
            try {
                collectibleImage = ImageIO.read(getClass().getResource(FREEZE_COLLECTABLE_IMAGE_PATH));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void applyEffects(Player player) {
        player.setXSpeed(player.getXSpeed()- 3);
    }

    @Override
    public void applyEffects(ArrayList<Player> players) {
        for(Player player: players){
            applyEffects(player);
        }
    }
}
