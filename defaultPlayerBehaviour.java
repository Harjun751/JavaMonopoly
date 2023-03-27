import java.util.concurrent.ThreadLocalRandom;

public class defaultPlayerBehaviour implements PlayerStateBehaviour{
    Player player;

    public defaultPlayerBehaviour(Player Player) {
        this.player = Player;
    }

    public void doTurn(){
        // Roll dice
        int diceRoll1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int diceRoll2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        player.movePlayer(diceRoll1 + diceRoll2);
        player.setDiceroll(diceRoll1 + diceRoll2);
        player.handlePlayerLanding();
    }
}
