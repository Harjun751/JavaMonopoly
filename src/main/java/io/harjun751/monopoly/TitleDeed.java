package io.harjun751.monopoly;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;

public class TitleDeed extends PropertySpace{
    private double houseCost;
    private double rent;
    private double rent1House;
    private double rent2House;
    private double rent3House;
    private double rent4House;
    private double rentHotel;
    private int color;

    public ArrayList<House> houses;
    public Hotel hotel;

    public TitleDeed(int Position, String Name, double BuyCost, double MortgagePrice, int Color,double HouseCost,double Rent, double Rent1House, double Rent2House, double Rent3House, double Rent4House, double RentHotel) {
        super(Position, Name, BuyCost, MortgagePrice);
        houseCost = HouseCost;
        rent = Rent;
        color = Color;
        rent1House = Rent1House;
        rent2House = Rent2House;
        rent3House = Rent3House;
        rent4House = Rent4House;
        rentHotel = RentHotel;
        hotel = null;
        houses = new ArrayList<House>();
    }

    public void buyHouse(){
        // Check if owner has full color set
        // At the same time, check that the building is happening evenly
        int smallestHouseAmnt = 5;

        ArrayList<BoardSpace> boardSpaces = this.getOwner().getBoard().getBoardSpaces();
        List<TitleDeed> propertyColorSet = boardSpaces.stream()
                                                .filter(boardspace -> boardspace instanceof TitleDeed)
                                                .map(property -> (TitleDeed)property)
                                                .filter(property -> property.color == this.color)
                                                .collect(Collectors.toList());

        for (TitleDeed property : propertyColorSet){
            if (property.getOwner() != this.getOwner()){
//                System.out.println("Oi mate you don't have a loicense for that (No color set)");
                return;
            }
            if (property.houses.size() <= smallestHouseAmnt){
                smallestHouseAmnt = property.houses.size();
            }
        }

        // Check if owner is building evenly
        if (this.houses.size()>smallestHouseAmnt){
//            System.out.println("Oi mate you don't have a loicense for that (Not building evenly)");
            return;
        }

        // Buy house from banker
        if (houses.size()<4){
            this.getOwner().pay(houseCost, this.getOwner().getBoard().getBanker());
            this.houses.add(new House());
        } else if (houses.size()==4){
            this.getOwner().pay(houseCost, this.getOwner().getBoard().getBanker());
            this.hotel = new Hotel();
        }

//        System.out.println("Congratulations mate your building has been approved by the government.");
    }

    public double getRent(){
        // Check for full color set
        boolean isFullColorSet = true;
        ArrayList<BoardSpace> boardSpaces = this.getOwner().getBoard().getBoardSpaces();
        List<TitleDeed> propertyColorSet = boardSpaces.stream()
                                                .filter(boardspace -> boardspace instanceof TitleDeed)
                                                .map(property -> (TitleDeed)property)
                                                .filter(property -> property.color == this.color)
                                                .collect(Collectors.toList());

        for (TitleDeed property : propertyColorSet){
            if (property.getOwner() != this.getOwner()){
                isFullColorSet = false;
                break;
            }
        }

        // Full color set but no buildings
        if (isFullColorSet && houses.size()==0 && hotel==null){
            return rent*2;
        // Full color set but no hotel
        } else if (isFullColorSet && hotel==null){
            int amntOfHouse = houses.size();
            if (amntOfHouse==1){
                return rent1House;
            } else if (amntOfHouse==2) {
                return rent2House;
            } else if (amntOfHouse==3) {
                return rent3House;
            } else {
                return rent4House;
            }
        // Hotel
        } else if (hotel!=null) {
            return rentHotel;
        // Plain ol' rent
        } else {
            return rent;
        }
    }
    
    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }
    public double getHouseCost() {
        return houseCost;
    }
}
