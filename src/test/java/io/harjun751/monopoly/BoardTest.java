package io.harjun751.monopoly;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void testGetBoardSpaceReturnsCorrectSpace() {
        // set up board & space
        Board board = new Board();
        ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
        TitleDeed sengkang = new TitleDeed(5, "Sengkang",100, 0, 2, 0, 0, 0, 0, 0, 0, 0);
        spaces.add(sengkang);
        board.setBoardSpaces(spaces);

        // assert
        assertEquals(board.getBoardSpace(5), sengkang);
    }

    @Test
    void testGetBoardSpaceReturnsNullWhenEmpty() {
        // set up board & space
        Board board = new Board();
        ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
        TitleDeed sengkang = new TitleDeed(5, "Sengkang",100, 0, 2, 0, 0, 0, 0, 0, 0, 0);
        spaces.add(sengkang);
        board.setBoardSpaces(spaces);

        // assert
        assertEquals(board.getBoardSpace(10), null);
    }

    @Test
    void testGetBoardSpaceReturnsNullForEmptySpace() {
        // set up board & space
        Board board = new Board();
        ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
        TitleDeed sengkang = new TitleDeed(5, "Sengkang",100, 0, 2, 0, 0, 0, 0, 0, 0, 0);
        spaces.add(sengkang);
        board.setBoardSpaces(spaces);

        // assert
        assertEquals(board.getBoardSpace(10), null);
    }

    @Test
    void testGetBoardSpaceReturnsCorrectSpaceNegative() {
        // set up board & space
        Board board = new Board();
        ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
        TitleDeed sengkang = new TitleDeed(5, "Sengkang",100, 0, 2, 0, 0, 0, 0, 0, 0, 0);
        TitleDeed bishan = new TitleDeed(6, "Bishan",100, 0, 2, 0, 0, 0, 0, 0, 0, 0);
        spaces.add(sengkang);
        spaces.add(bishan);
        board.setBoardSpaces(spaces);

        // assert
        assertNotEquals(board.getBoardSpace(5), bishan);
    }
}