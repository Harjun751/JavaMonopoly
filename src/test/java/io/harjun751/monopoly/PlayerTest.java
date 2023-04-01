package io.harjun751.monopoly;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testMovePlayerMoves() {
        // arrange
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(5);

        assertEquals(testPlayer.getCurrPosition(), 5);
    }

    @Test
    void testMovePlayerMovesFullCircle() {
        // arrange
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(41);

        assertEquals(testPlayer.getCurrPosition(), 1);
    }

    @Test
    void testMovePlayerMovesFullCircleAndCollectsGo() {
        // arrange
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(41);

        assertEquals(testPlayer.getCash(), 1500+200);
    }


    @Test
    void testSellHousesEvenlySellsOneHouse() {
        // arrange
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

    @Test
    void testPayCreditsRecipient(){
        // arrange
        // create board and player
        Board board = BoardBootstrapper.getBoard(2);
        Player testPayer = board.getPlayers().get(0);
        Player testRecipient = board.getPlayers().get(1);

        //act
        testPayer.pay(500, testRecipient);

        // assert
        assertEquals(2000, testRecipient.getCash());

    }

    @Test
    void testPayDebitsPayer(){
        // arrange
        // create board and player
        Board board = BoardBootstrapper.getBoard(2);
        Player testPayer = board.getPlayers().get(0);
        Player testRecipient = board.getPlayers().get(1);

        //act
        testPayer.pay(500, testRecipient);

        // assert
        assertEquals(1000, testPayer.getCash());
    }

    @Test
    void testPaySellsOneHouse(){
        // arrange
        // create board and player
        Board board = BoardBootstrapper.getBoard(2);
        Player testPayer = board.getPlayers().get(0);
        Player testRecipient = board.getPlayers().get(1);
        // set house owner and buy house
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPayer);
        sengkang.setOwner(testPayer);
        serangoon.setOwner(testPayer);
        // player cash = 1500 - 100 = 1400
        bidadari.buyHouse();

        //act
        testPayer.pay(1450,testRecipient);

        //assert
        assertEquals(0,testPayer.getCash());
        assertEquals(2950,testRecipient.getCash());
        assertEquals(0,bidadari.houses.size());
    }

    @Test
    void testPayMortgagesProperty(){
        // arrange
        // create board and player
        Board board = BoardBootstrapper.getBoard(2);
        Player testPayer = board.getPlayers().get(0);
        Player testRecipient = board.getPlayers().get(1);
        // set house owner and buy house
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPayer);
        sengkang.setOwner(testPayer);
        serangoon.setOwner(testPayer);
        // player cash = 1500 - 100 = 1400
        bidadari.buyHouse();

        //act
        testPayer.pay(1750,testRecipient);

        //assert - check if i should have this many asserts, possibly (probably) bad test design
        assertEquals(0,testPayer.getCash());
        assertEquals(1500+1750,testRecipient.getCash());
        assertEquals(0,bidadari.houses.size());
        assertEquals(true,bidadari.isMortgaged());
        assertEquals(true,sengkang.isMortgaged());
        assertEquals(true,serangoon.isMortgaged());
    }

    @Test
    void testPayReturnsFalseWhenTooExpensive(){
        // arrange
        // create board and player
        Board board = BoardBootstrapper.getBoard(2);
        Player testPayer = board.getPlayers().get(0);
        Player testRecipient = board.getPlayers().get(1);
        // set house owner and buy house
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        bidadari.setOwner(testPayer);

        //act
        boolean success = testPayer.pay(1601,testRecipient);

        //assert
        assertEquals(false, success);
    }

    @Test
    void testSellHousesEvenlySellsHotel(){
        // arrange
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);

        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);
        // each house costs 100 (for now) so 100*3*4 + 100 = 1300 total cost for house + hotel
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }
        bidadari.buyHouse();

        // 200 in bank

        // act
        testPlayer.sellHousesEvenly(Arrays.asList(bidadari,sengkang,serangoon), 250);

        assertEquals(null, bidadari.hotel);
        assertEquals(4, bidadari.houses.size());
        assertEquals(4, serangoon.houses.size());
        assertEquals(4, sengkang.houses.size());
    }

    @Test
    void testSellHousesEvenlySells2Hotels(){
        // arrange
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);

        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);
        // each house costs 100 (for now) so 100*3*4 + 100*2 = 1400 total cost for house + hotel
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }
        bidadari.buyHouse();
        serangoon.buyHouse();

        // 100 in bank

        // act
        testPlayer.sellHousesEvenly(Arrays.asList(bidadari,sengkang,serangoon), 200);

        assertEquals(null, bidadari.hotel);
        assertEquals(null, serangoon.hotel);
        assertEquals(4, bidadari.houses.size());
        assertEquals(4, serangoon.houses.size());
        assertEquals(4, sengkang.houses.size());
    }

    @Test
    void testSellHousesEvenlySells4HousesEvenly(){
        // arrange
        Board board = BoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);

        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);
        // each house costs 100 (for now) so 100*3*4 = 1200 total cost for house + hotel
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }

        // 300 in bank
        // sells at half price so 100/2 * 4 = 200
        // act
        testPlayer.sellHousesEvenly(Arrays.asList(bidadari,sengkang,serangoon), 500);

        assertEquals(2, bidadari.houses.size());
        assertEquals(3, serangoon.houses.size());
        assertEquals(3, sengkang.houses.size());
    }

}