public class goToJailAction extends SpecialActionCard{
    public void doAction(Player cardDrawer){
        cardDrawer.changeState(new jailPlayerBehaviour(cardDrawer));
    }
}
