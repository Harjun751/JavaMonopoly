package io.harjun751.monopoly;

public class payBankAction extends SpecialActionCard {
    private final double value;

    public payBankAction(double Value) {
        super("Pay " + Value + " to bank");
        value = Value;
    }

    public void doAction(Player cardDrawer) {
        // pay to bank
        cardDrawer.pay(value, cardDrawer.getBoard().getBanker());
    }
}
