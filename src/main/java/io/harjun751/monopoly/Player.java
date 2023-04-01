package io.harjun751.monopoly;

import java.lang.reflect.Array;
import java.util.*;
import java.util.concurrent.Flow;
import java.util.stream.*;


public class Player {
    private int id;
    private double cash;
    private int currPosition;
    private ArrayList<PropertySpace> properties;
    private ArrayList<getOutJailAction> goojCards;

    private PlayerStateBehaviour state;
    private int diceroll;
    private ArrayList<Subscriber> subscribers;

    private Board board;
    public static RandomNumberGeneratorInterface genny = new RandomNumberGenerator();


    public Player(int ID) {
        this.id = ID;
        this.cash = 1500;
        this.currPosition = 0;
        this.properties = new ArrayList<PropertySpace>();
        this.goojCards = new ArrayList<getOutJailAction>();
        this.state = new defaultPlayerState(this);
        this.subscribers = new ArrayList<Subscriber>();
        subscribers.add(StatisticsCollector.getInstance());
    }

    public int rollDice(){
        return genny.generateRandomNumber();
    }

    public Player(int ID, double Cash) {
        this.id = ID;
        this.cash = Cash;
        this.currPosition = 0;
        this.properties = new ArrayList<PropertySpace>();
        this.goojCards = new ArrayList<getOutJailAction>();
        this.state = new defaultPlayerState(this);
    }

    public void changeState(PlayerStateBehaviour State) {
        this.state = State;
    }

    public void doTurn() {
        // Pass call down to player state
        this.state.doTurn();
    }

    public int movePlayer(int roll) {
        int currentPosition = this.currPosition;
        // >38
        int finalPosition = currentPosition + roll;
        if (finalPosition > 39) {
            // pass go, collect 200
            this.board.getBanker().pay(200, this);
            // reset position
            finalPosition = finalPosition - 40;
        }
        this.setCurrPosition(finalPosition);
        return finalPosition;
    }

    public void handlePlayerLanding() {
        BoardSpace space = this.board.getBoardSpace(this.getCurrPosition());
//        System.out.println("i landed on " + this.getCurrPosition());
        this.notifySubscribers(StatisticType.LANDED, this);
        if (space instanceof PropertySpace) {
            PropertySpace propertyspace = (PropertySpace) space;
            if (propertyspace.isMortgaged()) {
                // nothing to do here
                return;
            }
            if (propertyspace.getOwner() != null && propertyspace.getOwner() != this) {
                // pay rent
                double rent = propertyspace.getRent();
                if (propertyspace instanceof Utilities) {
                    rent = rent * diceroll;
                }
                HashMap<String, Object> context = new HashMap<String, Object>();
                context.put("pos", this.getCurrPosition());
                context.put("rent", rent);
                this.notifySubscribers(StatisticType.RENTPAID, context);
                this.pay(rent, propertyspace.getOwner());
            } else {
                if (propertyspace.getOwner()==null && this.getCash() >= propertyspace.getBuyCost()) {
                    this.pay(propertyspace.getBuyCost(), this.board.getBanker());
//                    System.out.println("i bought popety");
                    propertyspace.setOwner(this);
                    if (this.properties.size()>1000){
                        System.out.println("wat");
                    }
                }
            }
        } else if (space instanceof TaxSpace) {
            TaxSpace tax = (TaxSpace) space;
            tax.payTax(this);
        } else if (space instanceof ChanceComSpace) {
            ChanceComSpace ccSpace = (ChanceComSpace) space;
            SpecialActionCard card = null;
            // TODO: REFACTOR
            if (ccSpace.isChanceSpace()) {
                card = this.board.getTopChanceCard();
                if (!(card instanceof getOutJailAction)) {
                    this.board.insertChanceCard(card);
                }
            } else {
                card = this.board.getTopComChestCard();
                if (!(card instanceof getOutJailAction)) {
                    this.board.insertComChestCard(card);
                }
            }
            card.doAction(this);
        } else if (space instanceof GoJailSpace) {
            this.changeState(new jailPlayerState(this));
        }
    }

    public boolean pay(double amnt, Player player) {
        // Attempt to pay with cash
        if (amnt < this.cash) {
            // debit from payer
            this.cash -= amnt;
            // credit to recepient
            player.cash += amnt;
            return true;
        } else {
            // oh boy.
            Map<Integer, List<TitleDeed>> deeds = this.properties.stream()
                    .filter(property -> property instanceof TitleDeed)
                    .map(property -> (TitleDeed) property)
                    .collect(Collectors.groupingBy(property -> property.getColor()));
            boolean raisedEnough = false;
            for (Integer key : deeds.keySet()) {
                List<TitleDeed> colorSet = deeds.get(key);
                raisedEnough = sellHousesEvenly(colorSet, amnt);
                if (raisedEnough) {
                    // debit from payer
                    this.cash -= amnt;
                    // credit to recipient
                    player.cash += amnt;
                    return true;
                }
            }
            // Mortgage Properties
            for (PropertySpace property : this.properties) {
                property.mortgage();
                if (this.cash >= amnt) {
                    // debit from payer
                    this.cash -= amnt;
                    // credit to recipient
                    player.cash += amnt;
                    return true;
                }
            }

            if (!raisedEnough) {
                // You're bankrupt.
                bankruptCleanup();
                return false;
            }
        }
        return false;
    }

    // Algo could probably use some cleanup
    public boolean sellHousesEvenly(List<TitleDeed> colorSet, double cost) {
        for (TitleDeed deed : colorSet) {
            if (deed.hotel != null) {
                // Banker pays player the cost of the hotel by half
                this.board.getBanker().pay(deed.getHouseCost() / 2, this);
                deed.hotel = null;
                if (this.cash >= cost) {
                    return true;
                } else {
                    return sellHousesEvenly(colorSet, cost);
                }
            }
        }
        int maxHouses = 0;
        TitleDeed deedHouseToSell = null;
        for (TitleDeed deed : colorSet) {
            if (deed.houses.size() > maxHouses) {
                deedHouseToSell = deed;
                maxHouses = deed.houses.size();
            }
        }

        if (deedHouseToSell != null) {
            this.board.getBanker().pay(deedHouseToSell.getHouseCost() / 2, this);
            deedHouseToSell.houses.remove(0);
            if (this.cash >= cost) {
                return true;
            } else {
                return sellHousesEvenly(colorSet, cost);
            }
        }
        return false;
    }

    public void bankruptCleanup() {
        // release properties back to market
        for (PropertySpace property : properties) {
            property.setOwner(null);
        }
        this.board.removeBankruptPlayer(this);
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

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public PlayerStateBehaviour getState() {
        return state;
    }

    public void subscribe(Subscriber subscriber) {
        this.subscribers.add(subscriber);
    }

    public void unsubscribe(Subscriber subscriber) {
        this.subscribers.remove(subscriber);
    }

    public void notifySubscribers(StatisticType type, Object context) {
        for (Subscriber sub : subscribers) {
            sub.update(type, context);
        }
    }
}