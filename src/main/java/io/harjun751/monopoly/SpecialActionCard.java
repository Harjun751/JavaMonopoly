package io.harjun751.monopoly;

public abstract class SpecialActionCard {
    public int id;
    public String description;
    public Board board;

    public abstract void doAction(Player cardDrawer);

    public SpecialActionCard(String description) {
        this.description = description;
    }
}
