package io.harjun751.monopoly;
public class Monopoly{
    static Board gameboard = BoardBootstrapper.getBoard(5);
    public static void main(String[] args) {
        gameboard.playGame();
    }
}