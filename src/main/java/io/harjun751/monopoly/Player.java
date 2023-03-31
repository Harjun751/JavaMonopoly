package io.harjun751.monopoly;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.Map;


public class Player {
    private int id;
    private double cash;
    private int currPosition;
    private ArrayList<PropertySpace> properties;
    private ArrayList<getOutJailAction> goojCards;
    private PlayerStateBehaviour state;
    private int diceroll;

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    private Board board;

    
    public Player(int ID) {
        this.id = ID;
        this.cash = 1500;
        this.currPosition = 0;
        this.properties = new ArrayList<PropertySpace>();
        this.goojCards = new ArrayList<getOutJailAction>();
        this.state = new defaultPlayerState(this);
    }

    public Player(int ID, double Cash){
        this.id = ID;
        this.cash = Cash;
        this.currPosition = 0;
        this.properties = new ArrayList<PropertySpace>();
        this.goojCards = new ArrayList<getOutJailAction>();
        this.state = new defaultPlayerState(this);
    }
    
    public void changeState(PlayerStateBehaviour State){
        this.state = State;
    }

    public void doTurn(){
        // Pass call down to player state
        this.state.doTurn();
    }

    public int movePlayer(int roll){
        int currentPosition = this.currPosition;
        // >38
        int finalPosition = currentPosition + roll;
        if (finalPosition>39){
            // pass go, collect 200
            this.board.getBanker().pay(200, this);
            // reset position
            finalPosition = finalPosition - 40;
        }
        this.setCurrPosition(finalPosition);
        return finalPosition;
    }

    public void handlePlayerLanding(){
        BoardSpace space = this.board.getBoardSpace(this.getCurrPosition());
        System.out.println("i landed on " + this.getCurrPosition());
        if (space instanceof PropertySpace){
            PropertySpace propertyspace = (PropertySpace) space;
            if (propertyspace.getOwner()!=null && propertyspace.getOwner()!=this){
                double rent = propertyspace.getRent();
                if (propertyspace instanceof Utilities){
                    rent = rent * diceroll;
                }
                this.pay(rent, propertyspace.getOwner());
            } else {
                if (this.getCash() >= propertyspace.getBuyCost()){
                    this.pay(propertyspace.getBuyCost(), this.board.getBanker());
                    System.out.println("i bought popety");
                    propertyspace.setOwner(this);
                    this.addProperties(propertyspace);
                }
            }
        } else if (space instanceof TaxSpace) {
            TaxSpace tax = (TaxSpace)space;
            tax.payTax(this);
        } else if (space instanceof ChanceComSpace){
            ChanceComSpace ccSpace = (ChanceComSpace)space;
            SpecialActionCard card = null;
            // TODO: REFACTOR
            if (ccSpace.isChanceSpace()){
                card = this.board.getTopChanceCard();
                if (!(card instanceof getOutJailAction)){
                    this.board.insertChanceCard(card);
                }
            } else {
                card = this.board.getTopComChestCard();
                if (!(card instanceof getOutJailAction)){
                    this.board.insertComChestCard(card);
                }
            }
            card.doAction(this);
        } else if (space instanceof GoJailSpace){
            this.changeState(new jailPlayerState(this));
        }
    }

    public void pay(double value, Player player){
        // Attempt to pay with cash
        if (value < this.cash){
            // debit from payer
            this.cash -= value;
            // credit to recepient
            player.cash += value;
        } else {
            // oh boy.
            Map<Integer, List<TitleDeed>> deeds = player.properties.stream()
                                                .filter(property -> property instanceof TitleDeed)
                                                .map(property -> (TitleDeed)property)
                                                .collect(Collectors.groupingBy(property -> property.getColor()));
            boolean raisedEnough = false;
            for (Integer key : deeds.keySet()){
                List<TitleDeed> colorSet = deeds.get(key);
                raisedEnough = sellHousesEvenly(colorSet, value);
                if (raisedEnough){
                    // debit from payer
                    this.cash -= value;
                    // credit to recepient
                    player.cash += value;
                    return;
                }
            }
            if (!raisedEnough){
                // You're bankrupt.
                bankruptCleanup();
            }
        }
    }

    // Algo could probably use some cleanup
    public boolean sellHousesEvenly(List<TitleDeed> colorSet, double cost){
        for (TitleDeed deed : colorSet){
            if (deed.hotel!=null){
                // Banker pays player the cost of the hotel by half
                this.board.getBanker().pay(deed.getHouseCost()/2, this);
                deed.hotel = null;
                if (this.cash>=cost){
                    return true;
                }
                else{
                    return sellHousesEvenly(colorSet, cost);
                }
            }
        }
        int maxHouses = 0;
        TitleDeed deedHouseToSell = null;
        for (TitleDeed deed : colorSet){
            if (deed.houses.size()>maxHouses){
                deedHouseToSell = deed;
            }
        }

        if (deedHouseToSell!=null){
            this.board.getBanker().pay(deedHouseToSell.getHouseCost()/2, this);
            deedHouseToSell.houses.remove(0);
            if (this.cash>=cost){
                return true;
            }
            else{
                return sellHousesEvenly(colorSet, cost);
            }
        }
        return false;
    }

    public void bankruptCleanup(){
        // release properties back to market
        for (PropertySpace property : properties){
            property.setOwner(null);
        }
    }



    // getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getCash() {
        return cash;
    }

    public void setCash(double cash) {
        this.cash = cash;
    }

    public int getCurrPosition() {
        return currPosition;
    }

    public void setCurrPosition(int currPosition) {
        this.currPosition = currPosition;
    }

    public ArrayList<PropertySpace> getProperties() {
        return properties;
    }

    public void addProperties(PropertySpace property) {
        this.properties.add(property);
    }

    public ArrayList<getOutJailAction> getGoojCards() {
        return goojCards;
    }

    public void addGoojCards(getOutJailAction goojCard) {
        this.goojCards.add(goojCard);
    }
    
    public void removeGoojCard() {
        this.goojCards.remove(0);
    }

    public int getDiceroll() {
        return diceroll;
    }

    public void setDiceroll(int diceroll) {
        this.diceroll = diceroll;
    }
}
