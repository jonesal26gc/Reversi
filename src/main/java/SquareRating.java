import java.util.List;

import static java.util.Arrays.asList;

public enum SquareRating {
    ROW_7(7,asList(9,0,0,0,0,0,0,9)),
    ROW_6(6,asList(0,0,0,0,0,0,0,0)),
    ROW_5(5,asList(0,0,9,9,9,9,0,0)),
    ROW_4(4,asList(0,0,9,0,0,9,0,0)),
    ROW_3(3,asList(0,0,9,0,0,9,0,0)),
    ROW_2(2,asList(0,0,9,9,9,9,0,0)),
    ROW_1(1,asList(0,0,0,0,0,0,0,0)),
    ROW_0(0,asList(9,0,0,0,0,0,0,9));

    private final int yAxis;
    private final List<Integer> preferenceRating;

    SquareRating(int yAxis, List<Integer> preferenceRating) {
        this.yAxis = yAxis;
        this.preferenceRating = preferenceRating;
    }

    public static int findRatingForSquare(int xAxis, int yAxis){
        for (SquareRating squarePreferenceRating : SquareRating.values()){
            if (squarePreferenceRating.yAxis == yAxis){
                return squarePreferenceRating.preferenceRating.get(xAxis);
            }
        }
        return 0;
    }
}
