package io.harjun751.monopoly;
public class payPlayerAction extends SpecialActionCard{
    private double value;

    public void doAction(Player cardDrawer){
        // each player in the game pays the card drawer
        for (Player player : cardDrawer.getBoard().getPlayers()) {
            cardDrawer.pay(value, player);
        }
    }

    public payPlayerAction(double Value) {
        value = Value;
    }
}
