package io.harjun751.monopoly;

import java.util.List;

public class advanceToRailwayAction extends SpecialActionCard {
    private final boolean payDouble;

    public advanceToRailwayAction(boolean PayDouble) {
        super("Advance to the nearest railway.");
        payDouble = PayDouble;
    }

    public void doAction(Player cardDrawer) {
        int currentPosition = cardDrawer.getCurrPosition();
        List<Integer> railwayPositions = cardDrawer.getBoard().getBoardSpaces().stream()
                .filter(boardspace -> boardspace instanceof Railway)
                .map(property -> ((Railway) property).getPosition())
                .toList();
        int minDifference = Integer.MAX_VALUE;
        int closestRailroad = Integer.MAX_VALUE;
        // find the closest forward railroad
        for (int i : railwayPositions) {
            int difference = i - currentPosition;
            if (difference <= 0) {
                // get forward difference
                // i.e. the steps the player would take if they went forward clockwise to reach the space
                difference = 40 + difference;
            }
            if (difference < minDifference) {
                minDifference = difference;
                closestRailroad = i;
            }
        }
        if (closestRailroad == Integer.MAX_VALUE) {
            // No railroad, silently continue game
        } else {
            cardDrawer.setCurrPosition(closestRailroad);
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

}
