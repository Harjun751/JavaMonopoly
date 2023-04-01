package io.harjun751.monopoly;
public abstract class BoardSpace {
    public int position;
    public String name;

    public BoardSpace(int Position, String Name) {
        position = Position;
        name = Name;
    }
}
