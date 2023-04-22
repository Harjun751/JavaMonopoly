package io.harjun751.monopoly;

import java.util.ArrayList;

public class BoardGameManager {
    private ArrayList<Subscriber> subscribers;
    private int numGames;
    private int maxTurns;
    private int currGame;
    private int players;

    public BoardGameManager(int numGames, int maxTurns, int players, ArrayList<Subscriber> subscribers) {
        this.subscribers = subscribers;
        this.numGames = numGames;
        this.maxTurns = maxTurns;
        this.players = players;
        this.currGame = 1;
    }

    public void play(){
        for (int i = 0; i < numGames; i++) {
            BoardBootstrapper.getBoard(players, maxTurns, subscribers).playGame();
            TurnTracker.getInstance().incrementGameCount();
        }
        TurnTracker.getInstance().resetGameCount();
    }


}
