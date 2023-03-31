package io.harjun751.monopoly;
public class payBankHouseHotelAction extends SpecialActionCard{
    private double perHouse;
    private double perHotel;

    public void doAction(Player cardDrawer){
        double toPay = 0.00;

        // assess the amount to pay
        for (PropertySpace property : cardDrawer.getProperties()) {
            if (property instanceof TitleDeed){
                TitleDeed titleDeed = (TitleDeed)property;
                toPay += (titleDeed.houses.size()*perHouse);
                if (titleDeed.hotel != null){
                    toPay += perHotel;
                }
            }
            
        }

        cardDrawer.pay(toPay, Monopoly.gameboard.getBanker());
    }

    public payBankHouseHotelAction(double PerHouse, double PerHotel) {
        perHotel = PerHotel;
        perHouse = PerHouse;
    }
}
