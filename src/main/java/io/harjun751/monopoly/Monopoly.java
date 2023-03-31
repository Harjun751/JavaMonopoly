package io.harjun751.monopoly;
public class Monopoly{
    static Board board = BoardBootstrapper.getBoard(5);
    public static void main(String[] args) {
        board.playGame();
    }
}