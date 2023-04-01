package io.harjun751.monopoly;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PropertySpaceTest {

    @Test
    void testRailwayGetsCorrectRentForOneInSet(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testOwner = board.getPlayers().get(0);
        PropertySpace space = (PropertySpace) board.getBoardSpace(5);

        //act
        space.setOwner(testOwner);

        //assert
        assertEquals(25, space.getRent());
    }

    @Test
    void testRailwayGetsCorrectRentForTwoInSet(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testOwner = board.getPlayers().get(0);
        PropertySpace railway1 = (PropertySpace) board.getBoardSpace(5);
        PropertySpace railway2 = (PropertySpace) board.getBoardSpace(15);

        //act
        railway1.setOwner(testOwner);
        railway2.setOwner(testOwner);

        //assert
        assertEquals(50, railway1.getRent());
        assertEquals(50, railway2.getRent());
    }

    @Test
    void testRailwayGetsCorrectRentForThreeInSet(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testOwner = board.getPlayers().get(0);
        PropertySpace railway1 = (PropertySpace) board.getBoardSpace(5);
        PropertySpace railway2 = (PropertySpace) board.getBoardSpace(15);
        PropertySpace railway3 = (PropertySpace) board.getBoardSpace(25);

        //act
        railway1.setOwner(testOwner);
        railway2.setOwner(testOwner);
        railway3.setOwner(testOwner);

        //assert
        assertEquals(100, railway1.getRent());
        assertEquals(100, railway2.getRent());
        assertEquals(100, railway3.getRent());
    }

    @Test
    void testRailwayGetsCorrectRentForFourInSet(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testOwner = board.getPlayers().get(0);
        PropertySpace railway1 = (PropertySpace) board.getBoardSpace(5);
        PropertySpace railway2 = (PropertySpace) board.getBoardSpace(15);
        PropertySpace railway3 = (PropertySpace) board.getBoardSpace(25);
        PropertySpace railway4 = (PropertySpace) board.getBoardSpace(35);

        //act
        railway1.setOwner(testOwner);
        railway2.setOwner(testOwner);
        railway3.setOwner(testOwner);
        railway4.setOwner(testOwner);

        //assert
        assertEquals(200, railway1.getRent());
        assertEquals(200, railway2.getRent());
        assertEquals(200, railway3.getRent());
        assertEquals(200, railway4.getRent());
    }

    @Test
    void testUtilityGetsCorrectRentForOneInSet(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testOwner = board.getPlayers().get(0);
        PropertySpace utility1 = (PropertySpace) board.getBoardSpace(12);

        //act
        utility1.setOwner(testOwner);

        //assert
        assertEquals(4, utility1.getRent());
    }

    @Test
    void testUtilityGetsCorrectRentForTwoInSet(){
        //arrange
        Board board = TestBoardBootstrapper.getBoard(1);
        Player testOwner = board.getPlayers().get(0);
        PropertySpace utility1 = (PropertySpace) board.getBoardSpace(12);
        PropertySpace utility2 = (PropertySpace) board.getBoardSpace(28);

        //act
        utility1.setOwner(testOwner);
        utility2.setOwner(testOwner);

        //assert
        assertEquals(10, utility1.getRent());
        assertEquals(10, utility2.getRent());
    }
}