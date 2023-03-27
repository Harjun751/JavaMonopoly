import java.util.stream.*;
import java.util.List;

public class Utilities extends PropertySpace{
    public Utilities(int Position, String Name, double BuyCost, double MortgagePrice) {
        super(Position, Name, BuyCost, MortgagePrice);
    }
    public double getRent(){
        List<Utilities> utilities = this.getOwner().getProperties().stream()
                                                .filter(boardspace -> boardspace instanceof Utilities)
                                                .map(property -> (Utilities)property)
                                                .collect(Collectors.toList());
        if (utilities.size()==1){
            return (4);
        } else {
            return (10);
        }
    }
}
