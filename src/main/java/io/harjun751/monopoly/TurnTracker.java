package io.harjun751.monopoly;

public class TurnTracker {
    private static TurnTracker instance;
    private int turnCount;

    public int getTurnCount() {
        return turnCount;
    }

    public int getGameCount() {
        return gameCount;
    }

    private int gameCount;

    private TurnTracker() {
        this.turnCount = 1;
        this.gameCount = 1;
    }

    public static TurnTracker getInstance() {
        if (instance == null) {
            TurnTracker tracker = new TurnTracker();
            instance = tracker;
        }
        return instance;
    }

    public void resetTurnCount(){
        this.turnCount=1;
    }
    public void resetGameCount(){
        this.gameCount=1;
    }

    public void incrementTurn(){
        this.turnCount++;
    }

    public void incrementGameCount(){
        this.gameCount++;
        this.resetTurnCount();
    }

}
