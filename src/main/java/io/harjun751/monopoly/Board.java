package io.harjun751.monopoly;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Board {
    private ArrayList<SpecialActionCard> chanceCards;
    private ArrayList<SpecialActionCard> comChestCards;
    private ArrayList<BoardSpace> boardSpaces;
    private final CopyOnWriteArrayList<Player> players;
    private Player banker;
    private int turns = 0;
    private int maxTurns;

    public Board(int maxTurns) {
        chanceCards = new ArrayList<SpecialActionCard>();
        comChestCards = new ArrayList<SpecialActionCard>();
        boardSpaces = new ArrayList<BoardSpace>();
        players = new CopyOnWriteArrayList<Player>();
        banker = null;
        this.maxTurns = maxTurns;
    }
    public Board() {
        chanceCards = new ArrayList<SpecialActionCard>();
        comChestCards = new ArrayList<SpecialActionCard>();
        boardSpaces = new ArrayList<BoardSpace>();
        players = new CopyOnWriteArrayList<Player>();
        banker = null;
    }

    public void playGame() {
        while (players.size() != 0) {
            for (Player player : players) {
                player.doTurn();
                turns += 1;
                TurnTracker.getInstance().incrementTurn();
                if (turns >= maxTurns) {
                    // end game by removing all players
                    for (Player remove : players) {
                        this.removeBankruptPlayer(remove);
                    }
                    break;
                }
            }
        }
    }

    public BoardSpace getBoardSpace(int position) {
        List<BoardSpace> space = this.boardSpaces.stream()
                .filter(boardspace -> boardspace.position == position)
                .toList();
        if (space.size() == 0) {
            return null;
        } else {
            return space.get(0);
        }
    }

    public void removeBankruptPlayer(Player player) {
        this.players.remove(player);
        if (players.size() == 1) {
            Player winner = players.get(0);
            this.players.remove(winner);
        }
    }


    // Getters/Setters
    public ArrayList<BoardSpace> getBoardSpaces() {
        return boardSpaces;
    }

    public void setBoardSpaces(ArrayList<BoardSpace> BoardSpaces) {
        boardSpaces = BoardSpaces;
    }

    public ArrayList<SpecialActionCard> getChanceCards() {
        return chanceCards;
    }

    public void setChanceCards(ArrayList<SpecialActionCard> ChanceCards) {
        Collections.shuffle(ChanceCards);
        this.chanceCards = ChanceCards;
    }

    public SpecialActionCard getTopChanceCard() {
        SpecialActionCard card = chanceCards.get(0);
        chanceCards.remove(0);
        return card;
    }

    public void insertChanceCard(SpecialActionCard card) {
        chanceCards.add(0, card);
    }

    public ArrayList<SpecialActionCard> getComChestCards() {
        return comChestCards;
    }

    public void setComChestCards(ArrayList<SpecialActionCard> ComChestCards) {
        Collections.shuffle(ComChestCards);
        this.comChestCards = ComChestCards;
    }

    public SpecialActionCard getTopComChestCard() {
        SpecialActionCard card = comChestCards.get(0);
        comChestCards.remove(0);
        return card;
    }

    public void insertComChestCard(SpecialActionCard card) {
        comChestCards.add(0, card);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void addPlayers(Player player) {
        this.players.add(player);
        player.setBoard(this);
    }

    public Player getBanker() {
        return banker;
    }

    public void setBanker(Player banker) {
        this.banker = banker;
    }
}
