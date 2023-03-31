package io.harjun751.monopoly;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testMovePlayerMoves() {
        // set up
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(5);

        assertEquals(testPlayer.getCurrPosition(), 5);
    }

    @Test
    void testMovePlayerMovesFullCircle() {
        // set up
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(41);

        assertEquals(testPlayer.getCurrPosition(), 1);
    }

    @Test
    void testMovePlayerMovesFullCircleAndCollectsGo() {
        // set up
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(41);

        assertEquals(testPlayer.getCash(), 1500+200);
    }


    @Test
    void testSellHousesEvenlySellsOneHouse() {
        // SET UP
        // create board and player
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        // Get pink color set and set owner to testPlayer
        TitleDeed woodlands = (TitleDeed) board.getBoardSpace(11);
        TitleDeed amk = (TitleDeed) board.getBoardSpace(13);
        TitleDeed tpy = (TitleDeed) board.getBoardSpace(14);
        woodlands.setOwner(testPlayer);
        amk.setOwner(testPlayer);
        tpy.setOwner(testPlayer);
        // Create house object and add it to house
        House house = new House();


    }

}