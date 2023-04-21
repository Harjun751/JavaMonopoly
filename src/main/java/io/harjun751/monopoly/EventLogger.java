package io.harjun751.monopoly;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class EventLogger implements Subscriber {
    private List<Log> logs = new ArrayList<>();


    public void update(EventType type, Object context) {
        if (type==EventType.LANDED){
            Player player = (Player) context;
            List<Player> playerList = player.getBoard().getPlayers().stream().toList();
            addToPlayerToLog(playerList);
        } else if (type==EventType.RENTPAID) {
            HashMap<String, Object> data = (HashMap<String, Object>) context;
            double rent = (double) data.get("rent");
            Integer player = (Integer) data.get("player");
            double cash = (double) data.get("cash");
            addStringToTurnLog(("Player "+ player + " paid rent of $" + rent + ", $" + cash + " remaining.\n"));
        } else if (type == EventType.BOUGHTPROPERTY) {
            HashMap<String, Object> data = (HashMap<String, Object>) context;
            double cost = (double) data.get("cost");
            Integer player = (Integer) data.get("player");
            double cash = (double) data.get("cash");
            addStringToTurnLog(("Player "+ player + " bought property for $" + cost + ", $" + cash + " remaining.\n"));
        } else if (type == EventType.GENERIC){
            addStringToTurnLog((String) context);
        }
    }

    public void addStringToTurnLog(String log){
        Log object = logs.get(logs.size() - 1);
        object.stringList.add(log);
    }

    public void addToPlayerToLog(List<Player> playerList){
        List<Player> deepCopyPlayerList = new ArrayList<>();
        for (Player player : playerList){
            Player deepCopy = new Player(player);
            deepCopyPlayerList.add(deepCopy);
        }
        TurnTracker tracker = TurnTracker.getInstance();
        Log object = new Log(deepCopyPlayerList,tracker.getTurnCount(),tracker.getGameCount());
        logs.add(object);
    }

    public void export() {
        try {
            FileWriter myWriter = new FileWriter("log.txt");
            for (Log log : logs){
                myWriter.write(log.toString());
            }
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
