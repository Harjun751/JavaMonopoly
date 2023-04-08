package io.harjun751.monopoly;

public abstract class PropertySpace extends BoardSpace {
    private int position;
    private String name;
    private double buyCost;
    private double mortgagePrice;
    private final double unmortgagePrice;
    private boolean isMortgaged;
    private Player owner;


    public PropertySpace(int Position, String Name, double BuyCost, double MortgagePrice, double UnmortgagePrice) {
        super(Position, Name);
        position = Position;
        name = Name;
        buyCost = BuyCost;
        mortgagePrice = MortgagePrice;
        unmortgagePrice = UnmortgagePrice;
        isMortgaged = false;
        owner = null;
    }

    public Player getOwner() {
        return owner;
    }

    public void setOwner(Player owner) {
        this.owner = owner;
        if (owner != null) {
            owner.addProperties(this);
        }
    }

    public abstract double getRent();

    public void mortgage() {
        if (!this.isMortgaged) {
            this.isMortgaged = true;
            this.owner.getBoard().getBanker().pay(this.mortgagePrice, this.owner);
        }
    }

    public void unmortgage() {
        if (this.isMortgaged) {
            if (this.owner.getCash() >= this.unmortgagePrice) {
                this.owner.pay(this.unmortgagePrice, this.owner.getBoard().getBanker());
            }
        }
    }

    // Getters and setters
    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getBuyCost() {
        return buyCost;
    }

    public void setBuyCost(double buyCost) {
        this.buyCost = buyCost;
    }

    public double getMortgagePrice() {
        return mortgagePrice;
    }

    public void setMortgagePrice(double mortgagePrice) {
        this.mortgagePrice = mortgagePrice;
    }

    public boolean isMortgaged() {
        return isMortgaged;
    }

    public void setMortgaged(boolean isMortgaged) {
        this.isMortgaged = isMortgaged;
    }
}
