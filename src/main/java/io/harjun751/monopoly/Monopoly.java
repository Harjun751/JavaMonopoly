package io.harjun751.monopoly;

public class Monopoly {
    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            BoardBootstrapper.getBoard(5).playGame();
            System.out.println(i + " finished");
        }
//        StatisticsCollector.getInstance().export();
//        EventLogger.getInstance().export();
    }
}