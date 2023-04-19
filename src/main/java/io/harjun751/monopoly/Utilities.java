package io.harjun751.monopoly;

import java.util.List;
import java.util.stream.Collectors;

public class Utilities extends PropertySpace {
    public Utilities(int Position, String Name, double BuyCost, double MortgagePrice, double UnmortgagePrice) {
        super(Position, Name, BuyCost, MortgagePrice, UnmortgagePrice);
    }

    public double getRent() {
        if (this.getOwner()==null){
            return 4;
        }
        List<Utilities> utilities = this.getOwner().getProperties().stream()
                .filter(boardspace -> boardspace instanceof Utilities)
                .map(property -> (Utilities) property)
                .collect(Collectors.toList());
        if (utilities.size() == 1) {
            return (4);
        } else {
            return (10);
        }
    }
}
