package io.harjun751.monopoly;

public class payBankHouseHotelAction extends SpecialActionCard {
    private final double perHouse;
    private final double perHotel;

    public payBankHouseHotelAction(double PerHouse, double PerHotel) {
        super("Pay to the bank " + PerHouse +" per house, " + PerHouse + " per hotel.");
        perHotel = PerHotel;
        perHouse = PerHouse;
    }

    public void doAction(Player cardDrawer) {
        double toPay = 0.00;

        // assess the amount to pay
        for (PropertySpace property : cardDrawer.getProperties()) {
            if (property instanceof TitleDeed titleDeed) {
                if (titleDeed.hotel != null) {
                    toPay += perHotel;
                } else {
                    toPay += (titleDeed.houses.size() * perHouse);
                }
            }
        }

        cardDrawer.pay(toPay, cardDrawer.getBoard().getBanker());
    }
}
