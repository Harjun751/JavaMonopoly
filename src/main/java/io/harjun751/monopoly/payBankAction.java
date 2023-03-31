package io.harjun751.monopoly;
public class payBankAction extends SpecialActionCard{
    private double value;
    
    public void doAction(Player cardDrawer){
        // pay to bank
        cardDrawer.pay(value, board.getBanker());
    }

    public payBankAction(double Value) {
        value = Value;
    }
}
