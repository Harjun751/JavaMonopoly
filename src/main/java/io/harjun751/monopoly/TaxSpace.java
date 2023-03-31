package io.harjun751.monopoly;
public class TaxSpace extends BoardSpace {
    public int position;
    public double value;

    public TaxSpace(int Position, double Value) {
        super(Position);
        position = Position;
        value = Value;
    }

    public void payTax(Player player){
        player.pay(value, Monopoly.gameboard.getBanker());
    }
}
