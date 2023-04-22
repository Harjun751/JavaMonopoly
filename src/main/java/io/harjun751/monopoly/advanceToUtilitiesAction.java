package io.harjun751.monopoly;

import java.util.List;
import java.util.stream.Collectors;

public class advanceToUtilitiesAction extends SpecialActionCard {
    private final boolean payTenTimes;

    public advanceToUtilitiesAction(boolean PayTenTimes) {
        super("Advance to the nearest Utility.");
        payTenTimes = PayTenTimes;
    }

    public void doAction(Player cardDrawer) {
        int currentPosition = cardDrawer.getCurrPosition();
        List<Integer> utilityPositions = cardDrawer.getBoard().getBoardSpaces().stream()
                .filter(boardspace -> boardspace instanceof Utilities)
                .map(property -> ((Utilities) property).getPosition())
                .toList();
        int minDifference = Integer.MAX_VALUE;
        int closestUtility = Integer.MAX_VALUE;
        // find the closest forward railroad
        for (int i : utilityPositions) {
            int difference = i - currentPosition;
            if (difference <= 0) {
                // get forward difference
                // i.e. the steps the player would take if they went forward clockwise to reach the space
                difference = 40 + difference;
            }
            if (difference < minDifference) {
                minDifference = difference;
                closestUtility = i;
            }
        }
        if (closestUtility == Integer.MAX_VALUE) {
            // No railroad, silently continue game
        } else {
            cardDrawer.setCurrPosition(closestUtility);
            if (payTenTimes) {
                BoardSpace space = cardDrawer.getBoard().getBoardSpace(cardDrawer.getCurrPosition());
                PropertySpace propertyspace = (PropertySpace) space;
                if (propertyspace.getOwner() != null) {
                    List<Utilities> utilities = propertyspace.getOwner().getProperties().stream()
                            .filter(boardspace -> boardspace instanceof Utilities)
                            .map(property -> (Utilities) property)
                            .collect(Collectors.toList());
                    if (utilities.size() == 1) {
                        // pay 6* of rent - other 4* is paid in handleplayerlanding
                        cardDrawer.pay(6 * cardDrawer.getDiceroll(), propertyspace.getOwner());
                    }
                    // if owner has both utils, no need to do anything
                }
            }
            cardDrawer.handlePlayerLanding();
        }
    }
}
