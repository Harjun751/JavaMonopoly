public abstract class SpecialActionCard {
    public int id;
    public String description;
    public Board board;

    public abstract void doAction(Player cardDrawer);
}
