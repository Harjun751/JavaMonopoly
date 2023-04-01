package io.harjun751.monopoly;

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
        System.out.println(landedMap);
        System.out.println(rentCollectedMap);
    }
    public static StatisticsCollector getInstance(){
        if (instance==null){
            StatisticsCollector collector = new StatisticsCollector();
            instance = collector;
        }
        return instance;
    }

    public void update(StatisticType type, Object context){
        if (type == StatisticType.LANDED){
            Player player = (Player) context;
            int landedAt = player.getCurrPosition();
            if (landedMap.containsKey(landedAt)){
                int landedValue = landedMap.get(landedAt);
                landedMap.put(landedAt, landedValue+1);
            } else {
                landedMap.put(landedAt, 1);
            }
        } else if (type == StatisticType.RENTPAID){
            HashMap data = (HashMap) context;
            double rent = (double) data.get("rent");
            int position = (int) data.get("pos");
            if (rentCollectedMap.containsKey(position)){
                double rentValue = rentCollectedMap.get(position);
                rentCollectedMap.put(position, rent+rentValue);
            } else {
                rentCollectedMap.put(position, rent);
            }
        }
    }

}
