package io.harjun751.monopoly;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public class Player {
    public static RandomNumberGeneratorInterface genny = new RandomNumberGenerator();
    private int id;
    private double cash;
    private int currPosition;
    private final ArrayList<PropertySpace> properties;
    private final ArrayList<getOutJailAction> goojCards;
    private PlayerStateBehaviour state;
    private int diceroll;
    private ArrayList<Subscriber> subscribers;
    private Board board;


    public Player(int ID) {
        this.id = ID;
        this.cash = 1500;
        this.currPosition = 0;
        this.properties = new ArrayList<PropertySpace>();
        this.goojCards = new ArrayList<getOutJailAction>();
        this.state = new defaultPlayerState(this);
        this.subscribers = new ArrayList<Subscriber>();
//        subscribers.add(StatisticsCollector.getInstance());
//        subscribers.add(EventLogger.getInstance());
    }

    public Player(int ID, double Cash) {
        this.id = ID;
        this.cash = Cash;
        this.currPosition = 0;
        this.properties = new ArrayList<PropertySpace>();
        this.goojCards = new ArrayList<getOutJailAction>();
        this.state = new defaultPlayerState(this);
    }

    public Player(Player another) {
        this.id = another.id;
        this.cash = another.cash;
        this.currPosition = another.currPosition;
        this.properties = another.properties;
        this.goojCards = another.goojCards;
        this.state = another.state;
    }

    public int rollDice() {
        return genny.generateRandomNumber();
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
        this.notifySubscribers(EventType.LANDED, this);
        if (space instanceof PropertySpace propertyspace) {
            if (propertyspace.isMortgaged()) {
                // nothing to do here
                return;
            }
            if (propertyspace.getOwner() != null && propertyspace.getOwner() != this) {
                // if property is owned by any other player - pay rent
                double rent = propertyspace.getRent();
                if (propertyspace instanceof Utilities) {
                    // Rent for utilities returns multiplier for dice roll
                    // Calculate actual rent from dice roll
                    rent = rent * diceroll;
                }
                // pay rent
                this.pay(rent, propertyspace.getOwner());
                // publish info to subscribers (logging)
                HashMap<String, Object> context = new HashMap<String, Object>();
                context.put("pos", this.getCurrPosition());
                context.put("rent", rent);
                context.put("player", this.id);
                context.put("cash", this.cash);
                this.notifySubscribers(EventType.RENTPAID, context);
            } else {
                if (propertyspace.getOwner() == null && this.getCash() >= propertyspace.getBuyCost()) {
                    // if property has no owner and player has enough cash to buy property - buy
                    this.pay(propertyspace.getBuyCost(), this.board.getBanker());
                    propertyspace.setOwner(this);
                    // publish info to subscribers (logging)
                    HashMap<String, Object> context = new HashMap<String, Object>();
                    context.put("cost", propertyspace.getBuyCost());
                    context.put("player", this.id);
                    context.put("cash", this.cash);
                    this.notifySubscribers(EventType.BOUGHTPROPERTY, context);
                }
            }
        } else if (space instanceof TaxSpace tax) {
            tax.payTax(this);
            this.notifySubscribers(EventType.GENERIC, this.id + " paid tax!\n");
        } else if (space instanceof ChanceComSpace ccSpace) {
            SpecialActionCard card = null;
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
            this.notifySubscribers(EventType.GENERIC, this.id + " drew a chance/coms card!\n");
        } else if (space instanceof GoJailSpace) {
            this.changeState(new jailPlayerState(this));
            this.notifySubscribers(EventType.GENERIC, this.id + " went to jail!\n");
        } else {
            this.notifySubscribers(EventType.GENERIC, this.id + " landed on nothing. Position: " + this.getCurrPosition() + "\n");
        }
    }

    public boolean pay(double amnt, Player player) {
        // Attempt to pay with cash
        if (amnt < this.cash) {
            // debit from payer
            this.cash -= amnt;
            // credit to recipient
            player.cash += amnt;
            return true;
        } else {
            // Start raising money to pay amount
            Map<Integer, List<TitleDeed>> deeds = this.properties.stream()
                    .filter(property -> property instanceof TitleDeed)
                    .map(property -> (TitleDeed) property)
                    .collect(Collectors.groupingBy(property -> property.getColor()));
            boolean raisedEnough = false;
            for (Integer key : deeds.keySet()) {
                List<TitleDeed> colorSet = deeds.get(key);
                raisedEnough = sellHousesEvenly(colorSet, amnt);
                if (raisedEnough) {
                    // pay rent and return
                    this.cash -= amnt;
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
            bankruptCleanup();
            return false;
        }
    }

    // Embrace the jank
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

        // Obtain the property with the most amount of houses in the color set,
        // and then sell the house on that property
        int maxHouses = 0;
        TitleDeed deedHouseToSell = null;
        for (TitleDeed deed : colorSet) {
            if (deed.houses.size() > maxHouses) {
                deedHouseToSell = deed;
                maxHouses = deed.houses.size();
            }
        }
        // Sell the house on the deed
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
        if (this.id == 1000) {
            // If the banker runs out of money, game is going on for too long
            System.out.println("Banker has run out of money. Stopping game.");
            for (Player player : board.getPlayers()) {
                board.removeBankruptPlayer(player);
            }
        } else {
            this.board.removeBankruptPlayer(this);
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

    public void notifySubscribers(EventType type, Object context) {
        for (Subscriber sub : subscribers) {
            sub.update(type, context);
        }
    }
}
