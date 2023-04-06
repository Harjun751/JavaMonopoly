package io.harjun751.monopoly;

import java.util.ArrayList;

// The purpose of this class is to provide a standalone test board
// that is decoupled from the actual game board in "BoardBootstrapper",
// as the property specifications defined there may change and affect
// the test results.
final class TestBoardBootstrapper {
//    public static Board getBoard(int playerCount){
//        Board board = new Board();
//
//
//        // initialize the special action cards
//        ArrayList<SpecialActionCard> communityChestCards = new ArrayList<SpecialActionCard>();
//        communityChestCards.add(new payBankHouseHotelAction(40,115));
//        communityChestCards.add(new getOutJailAction());
//        communityChestCards.add(new collectBankAction(10));
//        communityChestCards.add(new collectBankAction(50));
//        communityChestCards.add(new collectBankAction(200));
//        communityChestCards.add(new collectBankAction(100));
//        communityChestCards.add(new collectPlayerAction(10));
//        communityChestCards.add(new collectBankAction(25));
//        communityChestCards.add(new goToJailAction());
//        communityChestCards.add(new payBankAction(100));
//        communityChestCards.add(new collectBankAction(20));
//        communityChestCards.add(new payBankAction(50));
//        communityChestCards.add(new collectBankAction(100));
//        communityChestCards.add(new payBankAction(50));
//        communityChestCards.add(new advanceAction(false,0));
//        communityChestCards.add(new collectBankAction(100));
//        board.setComChestCards(communityChestCards);
//
//        ArrayList<SpecialActionCard> chanceCards = new ArrayList<SpecialActionCard>();
//        chanceCards.add(new getOutJailAction());
//        // TODO: CHANGE THIS TO ADVANCE TO MRT CARD
//        chanceCards.add(new advanceAction(true, 25));
//        chanceCards.add(new advanceAction(false, 24));
//        chanceCards.add(new collectBankAction(50));
//        chanceCards.add(new advanceAction(false, 0));
//        chanceCards.add(new payBankAction(15));
//        chanceCards.add(new payBankHouseHotelAction(25,100));
//        // TODO: CHANGE THIS TO ADVANCE TO UTILITIES
//        chanceCards.add(new advanceAction(true, 11));
//        chanceCards.add(new collectBankAction(150));
//        chanceCards.add(new goToJailAction());
//        chanceCards.add(new advanceAction(false, 5));
//        chanceCards.add(new advanceAction(false, 39));
//        chanceCards.add(new advanceAction(false, 11));
//        chanceCards.add(new payPlayerAction(50));
//        board.setChanceCards(chanceCards);
//
//
//
//
//
//        // Initialize players
//        for (int i = 0; i < playerCount; i++){
//            Player player = new Player(i);
//            board.addPlayers(player);
//        }
//        Player banker = new Player(1000,1000000000);
//        board.setBanker(banker);
//
//        // Initialize all boardSpaces
//        ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
//        spaces.add(new TaxSpace(4, 100));
//        spaces.add(new TaxSpace(38, 200));
//        spaces.add(new ChanceComSpace(2, false));
//        spaces.add(new ChanceComSpace(7, true));
//        spaces.add(new ChanceComSpace(17, false));
//        spaces.add(new ChanceComSpace(22, true));
//        spaces.add(new ChanceComSpace(33, false));
//        spaces.add(new ChanceComSpace(36, true));
//        spaces.add(new GoJailSpace(30));
//        spaces.add(new TitleDeed(1, "Joo Koon",60, 0, 1, 0, 50, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(3, "Bukit Gombak",60, 0, 1, 0, 60, 0, 0, 0, 0, 0));
//        spaces.add(new Railway(5,"Punggol MRT",200,0));
//        spaces.add(new TitleDeed(6, "Sengkang",100, 100, 2, 100, 100, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(8, "Bidadari",100, 100, 2, 100, 100, 300, 0, 0, 0, 500));
//        spaces.add(new TitleDeed(9, "Serangoon",120, 100, 2, 100, 120, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(11, "Woodlands",140, 0, 3, 0, 120, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(13, "Ang Mo Kio",140, 0, 3, 0, 120, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(14, "Toa Payoh",160, 0, 3, 0, 140, 0, 0, 0, 0, 0));
//        spaces.add(new Utilities(12, "Electric Company", 150, 0));
//        spaces.add(new Railway(15,"Bishan MRT",200,0));
//        spaces.add(new TitleDeed(16, "Tanjong Katong",180, 0, 3, 0, 140, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(18, "East Coast Road",180, 0, 3, 0, 140, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(19, "Bayshore Road",200, 0, 3, 0, 160, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(21, "Queenstown",220, 0, 3, 0, 160, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(23, "Tiong Bahru",220, 0, 3, 0, 160, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(24, "Tanjong Pagar",240, 0, 3, 0, 180, 0, 0, 0, 0, 0));
//        spaces.add(new Railway(25,"Botanic Garden MRT",200,0));
//        spaces.add(new TitleDeed(26, "Novena",260, 0, 3, 0, 180, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(27, "Holland Road",260, 0, 3, 0, 180, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(29, "Bukit Timah",280, 0, 3, 0, 200, 0, 0, 0, 0, 0));
//        spaces.add(new Utilities(28, "Water Works", 150, 0));
//        spaces.add(new GoJailSpace(30));
//        spaces.add(new TitleDeed(31, "River Valley Road",300, 0, 3, 0, 200, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(32, "Orchard Road",300, 0, 3, 0, 200, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(34, "Oxley Road",320, 0, 3, 0, 220, 0, 0, 0, 0, 0));
//        spaces.add(new Railway(35,"City Hall MRT",200,0));
//        spaces.add(new TitleDeed(37, "Marina Bay",350, 0, 3, 0, 300, 0, 0, 0, 0, 0));
//        spaces.add(new TitleDeed(39, "Sentosa Cove",400, 0, 3, 0, 400, 0, 0, 0, 0, 0));
//        board.setBoardSpaces(spaces);
//
//
//
//
//        return board;
//    }

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
        Player banker = new Player(1000,1000000000);
        banker.setBoard(board);
        board.setBanker(banker);

        // Initialize all boardSpaces
        ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();
        spaces.add(new TaxSpace(4, 200));
        spaces.add(new TaxSpace(38, 100));
        spaces.add(new ChanceComSpace(2, false));
        spaces.add(new ChanceComSpace(7, true));
        spaces.add(new ChanceComSpace(17, false));
        spaces.add(new ChanceComSpace(22, true));
        spaces.add(new ChanceComSpace(33, false));
        spaces.add(new ChanceComSpace(36, true));
        spaces.add(new TitleDeed(1,1, "Joo Koon", 60,2,10,30,90,160,250,50,54,66));
        spaces.add(new TitleDeed(3,1, "Bukit Gombak",60,2,10,30,90,160,250,50,54,66));
        spaces.add(new Railway(5,"Punggol MRT",200,100,110));
        spaces.add(new TitleDeed(6,2, "Sengkang",100, 6,30,90,270,400,550,50,54,66));
        spaces.add(new TitleDeed(8,2, "Bidadari",100,6,30,90,270,400,550,50,54,66));
        spaces.add(new TitleDeed(9,2, "Serangoon",120,  8,40,100,300,450,600,50,54,66));
        spaces.add(new TitleDeed(11, 3,"Woodlands",140, 10,50,150,450,625,750,100,110,114));
        spaces.add(new TitleDeed(13, 3,"Ang Mo Kio",140,10,50,150,450,625,750,100,110,114));
        spaces.add(new TitleDeed(14, 3,"Toa Payoh",160, 12,60,180,500,700,900,100,110,114));
        spaces.add(new Utilities(12, "Electric Company", 150, 75,83));
        spaces.add(new Railway(15,"Bishan MRT",200,100,110));
        spaces.add(new TitleDeed(16, 4, "Tanjong Katong",180, 14,70,200,550,750,950,100,110, 114));
        spaces.add(new TitleDeed(18, 4, "East Coast Road",180,14,70,200,550,750,950,100,110, 114));
        spaces.add(new TitleDeed(19, 4, "Bayshore Road",200, 16,80,220,600,800,1000,100,110,114));
        spaces.add(new TitleDeed(21, 5,"Queenstown",220, 18,90,250,700,875,1050,150,110,121));
        spaces.add(new TitleDeed(23, 5,"Tiong Bahru",220,18,90,250,700,875,1050,150,110,121));
        spaces.add(new TitleDeed(24, 5,"Tanjong Pagar",240, 20,100,300,750,925,1100,150,120,132));
        spaces.add(new Railway(25,"Botanic Garden MRT",200,100,110));
        spaces.add(new TitleDeed(26, 6, "Novena",260, 22,110,330,800,975,1150,150,130,143));
        spaces.add(new TitleDeed(27, 6, "Holland Road",260,22,110,330,800,975,1150,150,130,143));
        spaces.add(new TitleDeed(29, 6, "Bukit Timah",280,24,120,360,850,1025,1200,150,140,154));
        spaces.add(new Utilities(28, "Water Works", 150, 75,83));
        spaces.add(new GoJailSpace(30));
        spaces.add(new TitleDeed(31, 7, "River Valley Road",300,26,130,390,900,1100,1275,200,150,165));
        spaces.add(new TitleDeed(32, 7, "Orchard Road",300, 26,130,390,900,1100,1275,200,150,165));
        spaces.add(new TitleDeed(34, 7, "Oxley Road",320, 28,150,450,1000,1200,1400,200,160,176));
        spaces.add(new Railway(35,"City Hall MRT",200,100,110));
        spaces.add(new TitleDeed(37,8, "Marina Bay",350, 35,175,500,110,1300,1500,200,175,193));
        spaces.add(new TitleDeed(39,8, "Sentosa Cove",400, 50,200,600,1400,1700,2000,200,200,220));
        board.setBoardSpaces(spaces);




        return board;
    }
}
