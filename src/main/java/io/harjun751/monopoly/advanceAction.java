package io.harjun751.monopoly;
public class advanceAction extends SpecialActionCard{
    private boolean payDouble;
    private int positionToBe;

    public void doAction(Player cardDrawer){
        if (cardDrawer.getCurrPosition()>positionToBe){
            // pass go, collect 200
            cardDrawer.getBoard().getBanker().pay(200, cardDrawer);
        }
        cardDrawer.setCurrPosition(positionToBe);
        if (payDouble){
            // If pay double, do one payment here and another in HandlePlayerLanding
            BoardSpace space = cardDrawer.getBoard().getBoardSpace(cardDrawer.getCurrPosition());
            PropertySpace propertyspace = (PropertySpace) space;
            if (propertyspace.getOwner()!=null){
                double rent = propertyspace.getRent();
                cardDrawer.pay(rent, propertyspace.getOwner());
            }
        }
        cardDrawer.handlePlayerLanding();
    }

    public advanceAction(boolean PayDouble, int PositionToBe) {
        positionToBe = PositionToBe;
        payDouble = PayDouble;
    }
}
