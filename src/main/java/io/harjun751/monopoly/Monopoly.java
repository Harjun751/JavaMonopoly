package io.harjun751.monopoly;

import java.util.ArrayList;

public class Monopoly {
    public static void main(String[] args) {
        EventLogger logger = new EventLogger();
        StatisticsCollector collector = new StatisticsCollector();
        ArrayList<Subscriber> subscriberList = new ArrayList<>();
        subscriberList.add(logger);
        subscriberList.add(collector);
        BoardGameManager manager = new BoardGameManager(100,1000,5,subscriberList);
        manager.play();
    }
}