package io.harjun751.monopoly;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TitleDeedTest {
    @Test
    void testBuyHouseFailsForNonColorSet() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        //act
        bidadari.buyHouse();

        // assert
        assertEquals(bidadari.houses.size(), 0);
    }

    @Test
    void testBuyHouseFailsForNonEqualBuilding() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        //act
        bidadari.buyHouse();
        bidadari.buyHouse();

        // assert
        assertEquals(bidadari.houses.size(), 1);
    }

    @Test
    void testBuyHouseAddsOneHouse() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);


        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        // act
        bidadari.buyHouse();

        // assert
        assertEquals(bidadari.houses.size(), 1);
    }

    @Test
    void testBuyHouseBuysEvenly() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);


        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        // act
        bidadari.buyHouse();
        sengkang.buyHouse();
        serangoon.buyHouse();
        bidadari.buyHouse();

        // assert
        assertEquals(bidadari.houses.size(), 2);
    }

    @Test
    void testBuyHouseDeductsMoney() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        // act
        bidadari.buyHouse();

        // assert
        assertEquals(testPlayer.getCash(), 1500-bidadari.getHouseCost());
    }

    @Test
    void testBuyHouseBuysHotel() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        // Money is not a problem
        testPlayer.setCash(1000000000);


        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        // act
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }
        bidadari.buyHouse();

        // assert
        assertInstanceOf(Hotel.class, bidadari.hotel);
    }

    @Test
    void testBuyHouseNoHotelOnFourthHouse() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        // Money is not a problem
        testPlayer.setCash(1000000000);


        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        // act
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }


        // assert
        assertEquals(bidadari.hotel, null);
    }

    @Test
    void testGetRentReturnsCorrectSingleRent(){
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);

        // act
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        bidadari.setOwner(testPlayer);

        //assert
        assertEquals(6, bidadari.getRent());
    }

    @Test
    void testGetRentReturnsCorrectFullSetRent(){
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);

        // act
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        //assert
        assertEquals(6*2, bidadari.getRent());
    }

    @Test
    void testGetRentReturnsCorrectOneHouseRent(){
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        // act
        bidadari.buyHouse();

        //assert
        assertEquals(30, bidadari.getRent());
    }

    @Test
    void testGetRentReturnsCorrectHotelRent(){
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);

        // act
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }
        bidadari.buyHouse();

        //assert
        assertEquals(550, bidadari.getRent());
    }

}