
public enum SquareType {
    EMPTY('.'),
    SUGGESTED('*'),
    WHITE('W'),
    BLACK('B');

    private char icon;

    SquareType(char icon) {
        this.icon = icon;
    }

    public char getIcon() {
        return icon;
    }
}
