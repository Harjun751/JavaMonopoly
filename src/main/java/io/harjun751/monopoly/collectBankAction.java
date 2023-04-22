package io.harjun751.monopoly;

public class collectBankAction extends SpecialActionCard {
    private final double value;

    public collectBankAction(double Value) {
        super("Collect " + Value + " from the bank.");
        value = Value;
    }

    public void doAction(Player cardDrawer) {
        // banker pays card value to player
        cardDrawer.getBoard().getBanker().pay(value, cardDrawer);
    }
}
