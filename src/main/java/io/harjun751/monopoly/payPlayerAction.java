package io.harjun751.monopoly;

public class payPlayerAction extends SpecialActionCard {
    private final double value;

    public payPlayerAction(double Value) {
        super("Pay every player " + Value+".");
        value = Value;
    }

    public void doAction(Player cardDrawer) {
        // card drawer pays each person in the game
        for (Player player : cardDrawer.getBoard().getPlayers()) {
            cardDrawer.pay(value, player);
        }
    }
}
