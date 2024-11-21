package CoreGame;

public class BoostCollectible extends Collectible {
    public BoostCollectible(int x, int y, String effect){
        super(x,y,effect);
    }

    @Override
    public void applyEffects(Player player) {
        player.setXSpeed(player.getXSpeed()+ 5);
    }


}
