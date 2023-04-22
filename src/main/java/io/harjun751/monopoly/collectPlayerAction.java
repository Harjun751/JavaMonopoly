package io.harjun751.monopoly;

public class collectPlayerAction extends SpecialActionCard {
    private final double value;

    public collectPlayerAction(double Value) {
        super("Collect " + Value + " from every player.");
        value = Value;
    }

    public void doAction(Player cardDrawer) {
        // each player in the game pays the card drawer
        for (Player player : cardDrawer.getBoard().getPlayers()) {
            if (player != cardDrawer) {
                player.pay(value, cardDrawer);
            }
        }
    }
}
