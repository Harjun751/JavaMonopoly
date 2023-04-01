package io.harjun751.monopoly;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    @Test
    void testMovePlayerMoves() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(5);

        assertEquals(testPlayer.getCurrPosition(), 5);
    }

    @Test
    void testMovePlayerMovesFullCircle() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(41);

        assertEquals(testPlayer.getCurrPosition(), 1);
    }

    @Test
    void testMovePlayerMovesFullCircleAndCollectsGo() {
        // arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.movePlayer(41);

        assertEquals(testPlayer.getCash(), 1500+200);
    }


    @Test
    void testSellHousesEvenlySellsOneHouse() {
        // arrange
        // create board and player
        Board board = TestBoardBootstrapper.getBoard(1);
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
        Board board = TestBoardBootstrapper.getBoard(2);
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
        Board board = TestBoardBootstrapper.getBoard(2);
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
        Board board = TestBoardBootstrapper.getBoard(2);
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
        Board board = TestBoardBootstrapper.getBoard(2);
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
        Board board = TestBoardBootstrapper.getBoard(2);
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
        Board board = TestBoardBootstrapper.getBoard(1);
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
        Board board = TestBoardBootstrapper.getBoard(1);
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
        Board board = TestBoardBootstrapper.getBoard(1);
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

    @Test
    void testHandlePlayerLandingBuysProperty(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(5);

        //act
        testPlayer.handlePlayerLanding();

        //assert
        PropertySpace property = (PropertySpace) board.getBoardSpace(5);
        assertEquals(testPlayer,property.getOwner());
    }

    @Test
    void testHandlePlayerLandingPaysRent(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(2);
        Player testPayer = board.getPlayers().get(0);
        Player testRecipient = board.getPlayers().get(1);
        testPayer.setCurrPosition(3);
        PropertySpace space = (PropertySpace) board.getBoardSpace(3);
        space.setOwner(testRecipient);

        //act
        testPayer.handlePlayerLanding();

        //assert
        assertEquals(1440, testPayer.getCash());
        assertEquals(1560, testRecipient.getCash());
    }

    @Test
    void testHandlePlayerLandingPaysTax(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(38);


        //act
        testPlayer.handlePlayerLanding();

        //assert
        assertEquals(1300, testPlayer.getCash());
    }

    @Test
    void testHandlePlayerLandingUsesChanceCard(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(2);


        //act
        testPlayer.handlePlayerLanding();

        //assert

    }

    @Test
    void testHandlePlayerLandingUsesCommunityCard(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(7);


        //act
        testPlayer.handlePlayerLanding();

        //assert

    }

    @Test
    void testHandlePlayerLandingGoesToJail(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(30);


        //act
        testPlayer.handlePlayerLanding();

        //assert
        assertInstanceOf(jailPlayerState.class, testPlayer.getState());
    }

    @Test
    void testHandlePlayerLandingPaysUtilities(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(2);
        Player testPlayer = board.getPlayers().get(0);
        Player testOwner = board.getPlayers().get(1);
        testPlayer.setCurrPosition(12);
        testPlayer.setDiceroll(12);
        PropertySpace space = (PropertySpace) board.getBoardSpace(12);
        space.setOwner(testOwner);

        //act
        testPlayer.handlePlayerLanding();

        //assert
        // Dice roll = 12
        // 12 * 4 = 48
        assertEquals(1452, testPlayer.getCash());
        assertEquals(1548, testOwner.getCash());
    }

    @Test
    void testHandlePlayerLandingDoesNothingInFreeParking(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(20);

        //act
        testPlayer.handlePlayerLanding();

        //assert
        assertEquals(1500, testPlayer.getCash());
    }


    @Test
    void testDefaultPlayerStateMovesPlayer(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        Player.genny = new RandomNumberGeneratorMock(new ArrayList<>(Arrays.asList(3,1)));

        //act
        testPlayer.doTurn();

        //assert
        assertEquals(4, testPlayer.getCurrPosition());

        // tear down
        Player.genny = (RandomNumberGeneratorInterface) new RandomNumberGenerator();
    }

    @Test
    void testDefaultPlayerStateDoublesGivesTwoTurns(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        Player.genny = new RandomNumberGeneratorMock(new ArrayList<>(Arrays.asList(6,6,1,2)));

        //act
        testPlayer.doTurn();

        //assert
        assertEquals(15, testPlayer.getCurrPosition());

        // tear down
        Player.genny = (RandomNumberGeneratorInterface) new RandomNumberGenerator();
    }

    @Test
    void testDefaultPlayerStateTriplesThrowPlayerInJail(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        Player.genny = new RandomNumberGeneratorMock(new ArrayList<>(Arrays.asList(6,6,1,1,2,2)));

        //act
        testPlayer.doTurn();

        //assert
        assertEquals(10, testPlayer.getCurrPosition());
        assertInstanceOf(jailPlayerState.class, testPlayer.getState());

        // tear down
        Player.genny = (RandomNumberGeneratorInterface) new RandomNumberGenerator();
    }

    @Test
    void testJailPlayerStateDoublesExitJail(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.changeState(new jailPlayerState(testPlayer));
        Player.genny = new RandomNumberGeneratorMock(new ArrayList<>(Arrays.asList(1,1)));

        //act
        testPlayer.doTurn();

        //assert
        assertEquals(12, testPlayer.getCurrPosition());
        assertInstanceOf(defaultPlayerState.class, testPlayer.getState());

        // tear down
        Player.genny = (RandomNumberGeneratorInterface) new RandomNumberGenerator();
    }

    @Test
    void testJailPlayerStateThreeTurnsExitJail(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.changeState(new jailPlayerState(testPlayer));
        Player.genny = new RandomNumberGeneratorMock(new ArrayList<>(Arrays.asList(1,2,2,3,3,1)));

        //act
        testPlayer.doTurn();
        testPlayer.doTurn();
        testPlayer.doTurn();

        //assert
        assertEquals(14, testPlayer.getCurrPosition());
        assertInstanceOf(defaultPlayerState.class, testPlayer.getState());

        // tear down
        Player.genny = (RandomNumberGeneratorInterface) new RandomNumberGenerator();
    }

    @Test
    void testJailPlayerStateGoojCardImmediatelyIsUsed(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.addGoojCards(new getOutJailAction());
        testPlayer.changeState(new jailPlayerState(testPlayer));

        //act
        testPlayer.doTurn();

        //assert
        assertEquals(10, testPlayer.getCurrPosition());
        assertInstanceOf(defaultPlayerState.class, testPlayer.getState());

        // tear down
        Player.genny = (RandomNumberGeneratorInterface) new RandomNumberGenerator();
    }
}