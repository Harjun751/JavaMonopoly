public class payPlayerAction extends SpecialActionCard{
    private double value;

    public void doAction(Player cardDrawer){
        // each player in the game pays the card drawer
        for (Player player : board.getPlayers()) {
            cardDrawer.pay(value, player);
        }
    }

    public payPlayerAction(double Value) {
        value = Value;
    }
}
