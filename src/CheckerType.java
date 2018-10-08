

public enum CheckerType {
    BLACK,
    RED,
    WHITE;

    @Override
    public String toString() {
        if (this == CheckerType.BLACK) {
            return "Black";
        }
        else if (this == CheckerType.RED) {
            return "Red";
        }
        else {
            return "White";
        }
    }
}
