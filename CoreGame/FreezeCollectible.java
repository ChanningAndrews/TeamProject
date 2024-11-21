package CoreGame;

import java.util.ArrayList;

public class FreezeCollectible extends Collectible{
    public FreezeCollectible(int x, int y, String effect){
        super(x,y,effect);
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
