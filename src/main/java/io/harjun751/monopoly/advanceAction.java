package io.harjun751.monopoly;

public class advanceAction extends SpecialActionCard {
    private final boolean payDouble;
    private int positionToBe;

    public advanceAction(boolean PayDouble, int PositionToBe) {
        super("Advance to position " + PositionToBe+".");
        positionToBe = PositionToBe;
        payDouble = PayDouble;
    }

    public void doAction(Player cardDrawer) {
        if (positionToBe < 0) {
            // if negative, card means to move back spaces by specified amount
            int position = cardDrawer.getCurrPosition();
            int finalPosition = position + positionToBe;
            if (finalPosition < 0) {
                // change to actual board position
                finalPosition = 40 + finalPosition;
            }
            this.positionToBe = finalPosition;
        } else {
            // if positive, card means to just go to space specified
            if (cardDrawer.getCurrPosition() > positionToBe) {
                // pass go, collect 200
                cardDrawer.getBoard().getBanker().pay(200, cardDrawer);
            }
        }
        cardDrawer.setCurrPosition(positionToBe);
        if (payDouble) {
            // If pay double, do one payment here and another in HandlePlayerLanding
            BoardSpace space = cardDrawer.getBoard().getBoardSpace(cardDrawer.getCurrPosition());
            PropertySpace propertyspace = (PropertySpace) space;
            if (propertyspace.getOwner() != null) {
                double rent = propertyspace.getRent();
                cardDrawer.pay(rent, propertyspace.getOwner());
            }
        }
        cardDrawer.handlePlayerLanding();
    }
}
