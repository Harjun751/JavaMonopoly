package io.harjun751.monopoly;
import java.util.ArrayList;

final class BoardBootstrapper {
    public static Board getBoard(int playerCount){
        Board board = new Board();


        // initialize the special action cards
        ArrayList<SpecialActionCard> communityChestCards = new ArrayList<SpecialActionCard>();
        communityChestCards.add(new payBankHouseHotelAction(40,115));
        communityChestCards.add(new getOutJailAction());
        communityChestCards.add(new collectBankAction(10));
        communityChestCards.add(new collectBankAction(50));
        communityChestCards.add(new collectBankAction(200));
        communityChestCards.add(new collectBankAction(100));
        communityChestCards.add(new collectPlayerAction(10));
        communityChestCards.add(new collectBankAction(25));
        communityChestCards.add(new goToJailAction());
        communityChestCards.add(new payBankAction(100));
        communityChestCards.add(new collectBankAction(20));
        communityChestCards.add(new payBankAction(50));
        communityChestCards.add(new collectBankAction(100));
        communityChestCards.add(new payBankAction(50));
        communityChestCards.add(new advanceAction(false,0));
        communityChestCards.add(new collectBankAction(100));
        board.setComChestCards(communityChestCards);

        ArrayList<SpecialActionCard> chanceCards = new ArrayList<SpecialActionCard>();
        chanceCards.add(new getOutJailAction());
        // TODO: CHANGE THIS TO ADVANCE TO MRT CARD
        chanceCards.add(new advanceAction(true, 24));
        chanceCards.add(new advanceAction(false, 23));
        chanceCards.add(new collectBankAction(50));
        chanceCards.add(new advanceAction(false, 0));
        chanceCards.add(new payBankAction(15));
        chanceCards.add(new payBankHouseHotelAction(25,100));
        // TODO: CHANGE THIS TO ADVANCE TO UTILITIES
        chanceCards.add(new advanceAction(true, 11));
        chanceCards.add(new collectBankAction(150));
        chanceCards.add(new goToJailAction());
        chanceCards.add(new advanceAction(false, 4));
        chanceCards.add(new advanceAction(false, 38));
        chanceCards.add(new advanceAction(false, 10));
        chanceCards.add(new payPlayerAction(50));
        board.setChanceCards(chanceCards);





        // Initialize players
        for (int i = 0; i < playerCount; i++){
            Player player = new Player(i);
            board.addPlayers(player);
        }
        Player banker = new Player(1000,1000000);
        board.setBanker(banker);

        // Initialize all boardSpaces
        ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
        spaces.add(new TaxSpace(4, 100));
        spaces.add(new TaxSpace(37, 100));
        spaces.add(new ChanceComSpace(2, false));
        spaces.add(new ChanceComSpace(6, true));
        spaces.add(new ChanceComSpace(16, false));
        spaces.add(new ChanceComSpace(21, true));
        spaces.add(new ChanceComSpace(32, false));
        spaces.add(new ChanceComSpace(35, true));
        spaces.add(new GoJailSpace(29));
        spaces.add(new TitleDeed(1, "Joo Koon",60, 0, 1, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(3, "Bukit Gombak",60, 0, 1, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Railway(4,"Punggol MRT",200,0));
        spaces.add(new TitleDeed(5, "Sengkang",100, 0, 2, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(7, "Bidadari",100, 0, 2, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(8, "Serangoon",120, 0, 2, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(10, "Woodlands",140, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(12, "Ang Mo Kio",140, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(13, "Toa Payoh",160, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Utilities(11, "Electric Company", 150, 0));
        spaces.add(new Railway(14,"Bishan MRT",200,0));
        spaces.add(new TitleDeed(15, "Tanjong Katong",180, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(17, "East Coast Road",180, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(18, "Bayshore Road",200, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(20, "Queenstown",220, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(22, "Tiong Bahru",220, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(23, "Tanjong Pagar",240, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Railway(24,"Botanic Garden MRT",200,0));
        spaces.add(new TitleDeed(25, "Novena",260, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(26, "Holland Road",260, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(28, "Bukit Timah",280, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Utilities(27, "Water Works", 150, 0));
        spaces.add(new GoJailSpace(29));
        spaces.add(new TitleDeed(30, "River Valley Road",300, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(31, "Orchard Road",300, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(33, "Oxley Road",320, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Railway(34,"City Hall MRT",200,0));
        spaces.add(new TitleDeed(36, "Marina Bay",350, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(38, "Sentosa Cove",400, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        board.setBoardSpaces(spaces);
        



        return board;
    }
}
