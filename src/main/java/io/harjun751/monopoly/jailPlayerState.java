package io.harjun751.monopoly;
import java.util.concurrent.ThreadLocalRandom;

public class jailPlayerState implements PlayerStateBehaviour {
    Player player;
    private int turnsInJail;

    public jailPlayerState(Player Player) {
        this.player = Player;
        this.turnsInJail = 0;
        player.setCurrPosition(9);
    }

    public void doTurn(){
        // Check for gooj card
        // if have, immediately expend it
        if (player.getGoojCards().size()>0){
            player.changeState(new defaultPlayerState(player));
            player.removeGoojCard();
            return;
        }

        // Roll dice
        int diceRoll1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        int diceRoll2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
        if (diceRoll1 == diceRoll2 || turnsInJail==3){
            // gooj!
            player.movePlayer(diceRoll1 + diceRoll2);

        } else{
            turnsInJail+=1;
        }
        player.handlePlayerLanding();
    }
}
