package io.harjun751.monopoly;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class StatisticsCollector implements Subscriber{

    private HashMap<Integer,Integer> landedMap;
    private HashMap<Integer,Double> rentCollectedMap;
    private static StatisticsCollector instance;

    private StatisticsCollector() {
        this.landedMap = new HashMap<Integer,Integer>();
        this.rentCollectedMap = new HashMap<Integer,Double>();
    }

    public void update(Player player){}

    public void export(){
        String text1 = "position, name, landings\n";
        String text2 = "position, name, rent collected\n";
        Board referenceBoard = BoardBootstrapper.getBoard(1);
        for (Integer key : landedMap.keySet()){
            BoardSpace space = referenceBoard.getBoardSpace(key);
            String name = "";
            if (space==null){
                name = "free parkin";
            }
            else{
                name = space.name;
            }
            text1 = text1 + (key + ", " + name + ", " + landedMap.get(key)+"\n");
        }
        for (Integer key : landedMap.keySet()){
            BoardSpace space = referenceBoard.getBoardSpace(key);
            String name = "";
            if (space==null){
                name = "free parkin";
            }
            else{
                name = space.name;
            }
            text2 = text2 + (key + ", " + name + ", " + rentCollectedMap.get(key)+"\n");
        }
        System.out.println(text1);
        System.out.println(text2);
        try {
            FileWriter myWriter = new FileWriter("landedmap.csv");
            myWriter.write(text1);
            myWriter.close();
            FileWriter myWriter2 = new FileWriter("rentmap.csv");
            myWriter2.write(text2);
            myWriter2.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    public static StatisticsCollector getInstance(){
        if (instance==null){
            StatisticsCollector collector = new StatisticsCollector();
            instance = collector;
        }
        return instance;
    }

    public void update(EventType type, Object context){
        if (type == EventType.LANDED){
            Player player = (Player) context;
            int landedAt = player.getCurrPosition();
            if (landedMap.containsKey(landedAt)){
                int landedValue = landedMap.get(landedAt);
                landedMap.put(landedAt, landedValue+1);
            } else {
                landedMap.put(landedAt, 1);
            }
        } else if (type == EventType.RENTPAID){
            HashMap<String, Object> data = (HashMap <String, Object>) context;
            double rent = (double) data.get("rent");
            int position = (int) data.get("pos");
            if (rentCollectedMap.containsKey(position)){
                double rentValue = rentCollectedMap.get(position);
                // Add current rent collected to the total value of collected rent at that property
                rentCollectedMap.put(position, rent+rentValue);
            } else {
                rentCollectedMap.put(position, rent);
            }
        }
    }

}
