public class ChanceComSpace extends BoardSpace {
    private int position;
    private boolean isChanceSpace;

    public ChanceComSpace(int Position, boolean IsChanceSpace) {
        super(Position);
        position = Position;
        isChanceSpace = IsChanceSpace;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isChanceSpace() {
        return isChanceSpace;
    }

    public void setChanceSpace(boolean isChanceSpace) {
        this.isChanceSpace = isChanceSpace;
    }
}
