package io.harjun751.monopoly;

import java.util.List;
import java.util.stream.Collectors;

public class Railway extends PropertySpace {
    public Railway(int Position, String Name, double BuyCost, double MortgagePrice, double UnmortgagePrice) {
        super(Position, Name, BuyCost, MortgagePrice, UnmortgagePrice);
    }

    public double getRent() {
        if (this.getOwner()==null){
            return 25;
        }
        List<Railway> railways = this.getOwner().getProperties().stream()
                .filter(boardspace -> boardspace instanceof Railway)
                .map(property -> (Railway) property)
                .collect(Collectors.toList());
        if (railways.size() == 1) {
            return (25);
        } else if (railways.size() == 2) {
            return 50;
        } else if (railways.size() == 3) {
            return 100;
        } else {
            return 200;
        }
    }
}
