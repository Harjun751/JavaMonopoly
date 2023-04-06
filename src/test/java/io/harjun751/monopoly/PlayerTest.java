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
        // player cash = 1500 - 50 = 1450
        bidadari.buyHouse();
        // house sell back = 50/2 = 25

        //act
        testPayer.pay(1475,testRecipient);

        //assert
        assertEquals(0,testPayer.getCash());
        assertEquals(2975,testRecipient.getCash());
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
        // player cash = 1500 - 50 = 1450
        bidadari.buyHouse();
        // After selling house = 1450+25 = 1475
        // Mortgage value = 54+54+54 = 162
        // Total asset worth = 1450+25+162 = 1637

        //act
        testPayer.pay(1637,testRecipient);

        //assert - check if i should have this many asserts, possibly (probably) bad test design
        assertEquals(0,testPayer.getCash());
        assertEquals(1500+1637,testRecipient.getCash());
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
        // each house costs 50 so 50*3*4 + 50 = 650 total cost for house + hotel
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }
        bidadari.buyHouse();

        // 1500-650 = 850 in bank

        // act
        // Make player sell off exactly one house. 850 + 25 (house sell value) = 875
        testPlayer.sellHousesEvenly(Arrays.asList(bidadari,sengkang,serangoon), 875);

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
        // each house costs 50 (for now) so 50*3*4 + 50*2 = 700 total cost for house + hotel
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }
        bidadari.buyHouse();
        serangoon.buyHouse();

        // 800 in bank

        // act
        // Make player sell off two houses. 800 + 25*2 (house sell value) = 850
        testPlayer.sellHousesEvenly(Arrays.asList(bidadari,sengkang,serangoon), 850);

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
        // each house costs 50 (for now) so 50*3*4 = 600 total cost for house
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }

        // 900 in bank
        // act
        // Make player sell off two houses. 900 + 25*4 (house sell value) = 1000
        testPlayer.sellHousesEvenly(Arrays.asList(bidadari,sengkang,serangoon), 1000);

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
        //rent is 2
        testPayer.handlePlayerLanding();

        //assert
        assertEquals(1498, testPayer.getCash());
        assertEquals(1502, testRecipient.getCash());
    }

    @Test
    void testHandlePlayerLandingPaysTax(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(4);


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