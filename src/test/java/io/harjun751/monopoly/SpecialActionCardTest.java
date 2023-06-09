package io.harjun751.monopoly;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SpecialActionCardTest {
    @Test
    void testAdvanceActionMovesPlayer(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        SpecialActionCard advAction = new advanceAction(false,8);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(8, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceActionMovesPlayerAndCollectsGo(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(21);
        SpecialActionCard advAction = new advanceAction(false,20);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(1700, testPlayer.getCash());
    }

    @Test
    void testAdvanceActionMovesPlayerAndPaysDoubleRent(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(2);
        Player testPlayer = board.getPlayers().get(0);
        Player testOwner = board.getPlayers().get(1);
        PropertySpace space = (PropertySpace) board.getBoardSpace(6);
        space.setOwner(testOwner);
        SpecialActionCard advAction = new advanceAction(true,6);

        //act
        // rent is 6
        advAction.doAction(testPlayer);

        //assert
        assertEquals(1488, testPlayer.getCash());
        assertEquals(1512, testOwner.getCash());
    }

    @Test
    void testAdvanceActionMovesBackwards(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(8);
        SpecialActionCard advAction = new advanceAction(false,-3);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(5, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceActionMovesBackwardsPastGo(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(1);
        SpecialActionCard advAction = new advanceAction(false,-3);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(38, testPlayer.getCurrPosition());
    }

    @Test
    void testCollectBankActionGetsMoneyFromBank(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        SpecialActionCard collectBank = new collectBankAction(400);

        //act
        collectBank.doAction(testPlayer);

        //assert
        assertEquals(1900, testPlayer.getCash());
    }

    @Test
    void testCollectPlayerActionGetsMoneyFromPlayers(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(3);
        Player testRecipient = board.getPlayers().get(0);
        Player testPayer1 = board.getPlayers().get(1);
        Player testPayer2 = board.getPlayers().get(2);
        SpecialActionCard collectBank = new collectPlayerAction(50);

        //act
        collectBank.doAction(testRecipient);

        //assert
        assertEquals(1600, testRecipient.getCash());
        assertEquals(1450, testPayer1.getCash());
        assertEquals(1450, testPayer2.getCash());
    }

    @Test
    void testGOOJActionKeepsGOOJCard(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        SpecialActionCard gooj = new getOutJailAction();

        //act
        gooj.doAction(testPlayer);

        //assert
        assertEquals(1, testPlayer.getGoojCards().size());
    }

    @Test
    void testGoToJailActionBringsPlayerToJail(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        SpecialActionCard goToJail = new goToJailAction();

        //act
        goToJail.doAction(testPlayer);

        //assert
        assertInstanceOf(jailPlayerState.class, testPlayer.getState());
    }

    @Test
    void testPayBankActionDeductsCash(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        SpecialActionCard payBank = new payBankAction(1400);

        //act
        payBank.doAction(testPlayer);

        //assert
        assertEquals(100, testPlayer.getCash());
    }

    @Test
    void testPayBankHouseHotelActionDeductsCorrectly(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        SpecialActionCard payBank = new payBankHouseHotelAction(20,30);
        TitleDeed bidadari = (TitleDeed) board.getBoardSpace(8);
        TitleDeed sengkang = (TitleDeed) board.getBoardSpace(6);
        TitleDeed serangoon = (TitleDeed) board.getBoardSpace(9);
        bidadari.setOwner(testPlayer);
        sengkang.setOwner(testPlayer);
        serangoon.setOwner(testPlayer);
        // 4*2 houses + 1 hotel -> 160 + 30 = 190
        for (int i=0;i<4;i++){
            for (TitleDeed deed : Arrays.asList(bidadari,sengkang,serangoon)){
                deed.buyHouse();
            }
        }
        bidadari.buyHouse();
        double cashAfterBuyingHouses = testPlayer.getCash();

        //act
        payBank.doAction(testPlayer);

        //assert
        assertEquals(cashAfterBuyingHouses-190, testPlayer.getCash());
    }

    @Test
    void testPayPlayerActionPaysEveryPlayer(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(3);
        Player testPayer = board.getPlayers().get(0);
        Player testRecipient1 = board.getPlayers().get(1);
        Player testRecipient2 = board.getPlayers().get(2);
        SpecialActionCard payPlayerAction = new payPlayerAction(50);

        //act
        payPlayerAction.doAction(testPayer);

        //assert
        assertEquals(1400, testPayer.getCash());
        assertEquals(1550, testRecipient1.getCash());
        assertEquals(1550, testRecipient2.getCash());
    }

    @Test
    void testAdvanceToRailwayActionGoesToNearestRailway(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(16);
        SpecialActionCard advAction = new advanceToRailwayAction(false);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(25, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceToRailwayActionGoesToNearestRailwayInCompleteLoop(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(36);
        SpecialActionCard advAction = new advanceToRailwayAction(false);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(5, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceToRailwayActionGoesToNextRailway(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(25);
        SpecialActionCard advAction = new advanceToRailwayAction(false);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(35, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceToRailwayActionPaysRent(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(2);
        Player testPlayer = board.getPlayers().get(0);
        Player testOwner = board.getPlayers().get(1);
        testPlayer.setCurrPosition(16);
        SpecialActionCard advAction = new advanceToRailwayAction(false);
        PropertySpace space = (PropertySpace) board.getBoardSpace(25);
        space.setOwner(testOwner);

        //act - player pays double 25*2=50
        advAction.doAction(testPlayer);

        //assert
        assertEquals(25, testPlayer.getCurrPosition());
        assertEquals(1475, testPlayer.getCash());
    }

    @Test
    void testAdvanceToRailwayActionPaysDouble(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(2);
        Player testPlayer = board.getPlayers().get(0);
        Player testOwner = board.getPlayers().get(1);
        testPlayer.setCurrPosition(16);
        SpecialActionCard advAction = new advanceToRailwayAction(true);
        PropertySpace space = (PropertySpace) board.getBoardSpace(25);
        space.setOwner(testOwner);

        //act - player pays double 25*2=50
        advAction.doAction(testPlayer);

        //assert
        assertEquals(25, testPlayer.getCurrPosition());
        assertEquals(1450, testPlayer.getCash());
    }
//======================================================================================================================
    @Test
    void testAdvanceToUtilityActionGoesToNearestUtility(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(13);
        SpecialActionCard advAction = new advanceToUtilitiesAction(false);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(28, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceToUtilityActionGoesToNearestUtilityInCompleteLoop(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(29);
        SpecialActionCard advAction = new advanceToUtilitiesAction(false);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(12, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceToUtilityActionGoesToNextUtility(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testPlayer = board.getPlayers().get(0);
        testPlayer.setCurrPosition(12);
        SpecialActionCard advAction = new advanceToUtilitiesAction(false);

        //act
        advAction.doAction(testPlayer);

        //assert
        assertEquals(28, testPlayer.getCurrPosition());
    }

    @Test
    void testAdvanceToUtilityActionPaysRent(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(2);
        Player testPlayer = board.getPlayers().get(0);
        Player testOwner = board.getPlayers().get(1);
        testPlayer.setCurrPosition(12);
        SpecialActionCard advAction = new advanceToUtilitiesAction(false);
        PropertySpace space = (PropertySpace) board.getBoardSpace(28);
        space.setOwner(testOwner);
        testPlayer.setDiceroll(12);

        //act - player pays 12*4
        advAction.doAction(testPlayer);

        //assert
        assertEquals(28, testPlayer.getCurrPosition());
        assertEquals(1452, testPlayer.getCash());
    }

    @Test
    void testAdvanceToRailwayActionPaysTenTimesDiceRoll(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(2);
        Player testPlayer = board.getPlayers().get(0);
        Player testOwner = board.getPlayers().get(1);
        testPlayer.setCurrPosition(12);
        SpecialActionCard advAction = new advanceToUtilitiesAction(true);
        PropertySpace space = (PropertySpace) board.getBoardSpace(28);
        space.setOwner(testOwner);
        testPlayer.setDiceroll(12);

        //act - player pays double 12*10=120
        advAction.doAction(testPlayer);

        //assert
        assertEquals(28, testPlayer.getCurrPosition());
        assertEquals(1380, testPlayer.getCash());
    }


}