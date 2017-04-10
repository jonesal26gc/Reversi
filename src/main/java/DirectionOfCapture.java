
public enum DirectionOfCapture {
    N(0, +1),
    NE(+1, +1),
    E(+1, 0),
    SE(+1, -1),
    S(0, -1),
    SW(-1, -1),
    W(-1, 0),
    NW(-1, +1);

    private int xAxisOffset;
    private int yAxisOffset;

    DirectionOfCapture(int xAxisOffset, int yAxisOffset) {
        this.xAxisOffset = xAxisOffset;
        this.yAxisOffset = yAxisOffset;
    }

    public int getXAxisOffset() {
        return xAxisOffset;
    }

    public int getYAxisOffset() {
        return yAxisOffset;
    }

    @Override
    public String toString() {
        return "DirectionOfCapture{" +
                "xAxisOffset=" + xAxisOffset +
                ", yAxisOffset=" + yAxisOffset +
                '}';
    }
}
