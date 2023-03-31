package io.harjun751.monopoly;
public class collectBankAction extends SpecialActionCard{
    private double value;

    public void doAction(Player cardDrawer){
        // banker pays card value to player
        cardDrawer.getBoard().getBanker().pay(value, cardDrawer);
    }    
    public collectBankAction(double Value) {
        value = Value;
    }
}
