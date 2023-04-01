package io.harjun751.monopoly;
public class Monopoly{
//    static Board board = BoardBootstrapper.getBoard(5);
    public static void main(String[] args){
        for (int i = 0; i<20; i++){
            BoardBootstrapper.getBoard(5).playGame();
            System.out.println(i+ " finished");
        }
        StatisticsCollector.getInstance().export();
    }
}