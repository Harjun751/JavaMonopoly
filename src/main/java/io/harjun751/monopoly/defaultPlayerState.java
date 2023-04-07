package io.harjun751.monopoly;

public class defaultPlayerState implements PlayerStateBehaviour {
    Player player;

    public defaultPlayerState(Player Player) {
        this.player = Player;
    }

    public void doTurn() {
        boolean isMyTurn = true;
        int numberOfDouble = 0;
        while (isMyTurn) {
            // Roll dice
            int diceRoll1 = player.rollDice();
            int diceRoll2 = player.rollDice();
            player.movePlayer(diceRoll1 + diceRoll2);
            player.setDiceroll(diceRoll1 + diceRoll2);
            player.handlePlayerLanding();
            if (diceRoll1 == diceRoll2) {
                this.player.notifySubscribers(EventType.GENERIC, "Doubles! \n");
                numberOfDouble++;
                if (numberOfDouble >= 3) {
                    // go to jail, stop turn
                    player.changeState(new jailPlayerState(player));
                    isMyTurn = false;
                }
            } else {
                isMyTurn = false;
            }
        }
    }
}
