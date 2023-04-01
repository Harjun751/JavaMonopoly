package io.harjun751.monopoly;
public class collectPlayerAction extends SpecialActionCard{
    private double value;

    public void doAction(Player cardDrawer){
        // each player in the game pays the card drawer
        for (Player player : cardDrawer.getBoard().getPlayers()) {
            if (player != cardDrawer){
                player.pay(value, cardDrawer);
            }
        }
    }

    public collectPlayerAction(double Value) {
        value = Value;
    }
}
