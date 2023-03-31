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
        chanceCards.add(new advanceAction(true, 25));
        chanceCards.add(new advanceAction(false, 24));
        chanceCards.add(new collectBankAction(50));
        chanceCards.add(new advanceAction(false, 0));
        chanceCards.add(new payBankAction(15));
        chanceCards.add(new payBankHouseHotelAction(25,100));
        // TODO: CHANGE THIS TO ADVANCE TO UTILITIES
        chanceCards.add(new advanceAction(true, 11));
        chanceCards.add(new collectBankAction(150));
        chanceCards.add(new goToJailAction());
        chanceCards.add(new advanceAction(false, 5));
        chanceCards.add(new advanceAction(false, 39));
        chanceCards.add(new advanceAction(false, 11));
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
        spaces.add(new TaxSpace(38, 100));
        spaces.add(new ChanceComSpace(2, false));
        spaces.add(new ChanceComSpace(7, true));
        spaces.add(new ChanceComSpace(17, false));
        spaces.add(new ChanceComSpace(22, true));
        spaces.add(new ChanceComSpace(33, false));
        spaces.add(new ChanceComSpace(36, true));
        spaces.add(new GoJailSpace(29));
        spaces.add(new TitleDeed(1, "Joo Koon",60, 0, 1, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(3, "Bukit Gombak",60, 0, 1, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Railway(5,"Punggol MRT",200,0));
        spaces.add(new TitleDeed(6, "Sengkang",100, 0, 2, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(8, "Bidadari",100, 0, 2, 0, 100, 300, 0, 0, 0, 500));
        spaces.add(new TitleDeed(9, "Serangoon",120, 0, 2, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(11, "Woodlands",140, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(13, "Ang Mo Kio",140, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(14, "Toa Payoh",160, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Utilities(12, "Electric Company", 150, 0));
        spaces.add(new Railway(15,"Bishan MRT",200,0));
        spaces.add(new TitleDeed(16, "Tanjong Katong",180, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(18, "East Coast Road",180, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(19, "Bayshore Road",200, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(21, "Queenstown",220, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(23, "Tiong Bahru",220, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(24, "Tanjong Pagar",240, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Railway(25,"Botanic Garden MRT",200,0));
        spaces.add(new TitleDeed(26, "Novena",260, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(27, "Holland Road",260, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(29, "Bukit Timah",280, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Utilities(28, "Water Works", 150, 0));
        spaces.add(new GoJailSpace(30));
        spaces.add(new TitleDeed(31, "River Valley Road",300, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(32, "Orchard Road",300, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(34, "Oxley Road",320, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new Railway(35,"City Hall MRT",200,0));
        spaces.add(new TitleDeed(37, "Marina Bay",350, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        spaces.add(new TitleDeed(39, "Sentosa Cove",400, 0, 3, 0, 0, 0, 0, 0, 0, 0));
        board.setBoardSpaces(spaces);
        



        return board;
    }
}
