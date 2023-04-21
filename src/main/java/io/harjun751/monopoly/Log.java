package io.harjun751.monopoly;

import java.util.ArrayList;
import java.util.List;

public class Log {
    public List<Player> playerList;

    public List<String> stringList;

    public int turn;

    public int game;

    public Log(List<Player> playerList, int turn, int game) {
        this.playerList = playerList;
        this.stringList = new ArrayList<>();
        this.turn = turn;
        this.game = game;
    }

    @Override
    public String toString() {
        return "Game: " + game + " Turn: " + turn + stringList.toString()+"\n";
    }
}
