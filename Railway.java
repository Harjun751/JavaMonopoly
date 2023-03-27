public class Railway extends PropertySpace{
    public Railway(int Position, String Name, double BuyCost, double MortgagePrice) {
        super(Position, Name, BuyCost, MortgagePrice);
    }

    public double getRent(){
        return 0.0;
    }
}
