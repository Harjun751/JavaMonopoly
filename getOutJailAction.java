public class getOutJailAction extends SpecialActionCard {
    public void doAction(Player cardDrawer){
        cardDrawer.addGoojCards(this);
        return;
    }
}
