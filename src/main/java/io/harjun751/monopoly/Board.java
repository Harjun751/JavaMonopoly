package io.harjun751.monopoly;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.*;
import java.util.Collections;

public class Board {
    private ArrayList<SpecialActionCard> chanceCards;
    private ArrayList<SpecialActionCard> comChestCards;
    private ArrayList<BoardSpace> boardSpaces;
    private ArrayList<Player> players;
    private Player banker;

    public Board() {
        chanceCards = new ArrayList<SpecialActionCard> ();
        comChestCards = new ArrayList<SpecialActionCard> ();
        boardSpaces = new ArrayList<BoardSpace> ();
        players = new ArrayList<Player> ();
        banker = null;
    }

    public void playGame(){
        while (true){
            for (Player player : players){
                player.doTurn();
            }
        }
    }
    
    public BoardSpace getBoardSpace(int position){
        List<BoardSpace> space = this.boardSpaces.stream()
            .filter(boardspace -> boardspace.position == position)
            .collect(Collectors.toList());
        if (space.size()==0){
            return null;
        } else {
            return space.get(0);
        }
    }

    public void removeBankruptPlayer(Player player){
        this.players.remove(player);
        if (players.size()==1){
            System.out.println("Game ended! 1 player remaining!");
        }
        System.out.println("Bankrupted...");
    }



    // Getters/Setters
    public ArrayList<BoardSpace> getBoardSpaces(){
        return boardSpaces;
    }
    public void setBoardSpaces(ArrayList<BoardSpace> BoardSpaces){
        boardSpaces = BoardSpaces;
    }
    public ArrayList<SpecialActionCard> getChanceCards(){
        return chanceCards;
    }
    public SpecialActionCard getTopChanceCard(){
        SpecialActionCard card = chanceCards.get(0);
        chanceCards.remove(0);
        return card;
    }
    public void insertChanceCard(SpecialActionCard card){
        chanceCards.add(0, card);
    }
    public void setChanceCards(ArrayList<SpecialActionCard> ChanceCards){
        Collections.shuffle(ChanceCards);
        this.chanceCards = ChanceCards;
    }
    public ArrayList<SpecialActionCard> getComChestCards(){
        return comChestCards;
    }
    public SpecialActionCard getTopComChestCard(){
        SpecialActionCard card = comChestCards.get(0);
        comChestCards.remove(0);
        return card;
    }
    public void insertComChestCard(SpecialActionCard card){
        comChestCards.add(0, card);
    }
    public void setComChestCards(ArrayList<SpecialActionCard> ComChestCards){
        Collections.shuffle(ComChestCards);
        this.comChestCards = ComChestCards;
    }
    public ArrayList<Player> getPlayers() {
        return players;
    }
    public void addPlayers(Player player) {
        this.players.add(player);
    }
    public Player getBanker() {
        return banker;
    }
    public void setBanker(Player banker) {
        this.banker = banker;
    }
}
