package io.harjun751.monopoly;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;


public class EventLogger implements Subscriber{
    private static EventLogger instance;
    private String finalLogString = "";
    public static EventLogger getInstance(){
        if (instance==null){
            EventLogger collector = new EventLogger();
            instance = collector;
        }
        return instance;
    }

    public void update(EventType type, Object context){
        String logstring = "";
        if (type == EventType.RENTPAID){
            HashMap<String, Object> data = (HashMap <String, Object>) context;
            double rent = (double) data.get("rent");
            Integer player = (Integer) data.get("player");
            double cash = (double) data.get("cash");
            logstring =  player +" paid " + rent + ". $" + cash +" remaining.\n";
        } else if (type == EventType.BOUGHTPROPERTY) {
            HashMap<String, Object> data = (HashMap<String, Object>) context;
            double cost = (double) data.get("cost");
            Integer player = (Integer) data.get("player");
            double cash = (double) data.get("cash");
            logstring = player + " bought property for " + cost + ". $" + cash + " remaining.\n";
        } else if (type == EventType.GENERIC){
            logstring = (String) context;
        }
        finalLogString = finalLogString.concat(logstring);
    }

    public void export(){
        try {
            FileWriter myWriter = new FileWriter("log.txt");
            myWriter.write(finalLogString);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
