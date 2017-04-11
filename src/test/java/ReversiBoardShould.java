import org.junit.Test;

import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ReversiBoardShould {
    private static final char WHITE_CELL = 'W';
    private static final char BLACK_CELL = 'B';
    private static final char[] xAxisLabels = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private static final char[] yAxisLabels = {'1', '2', '3', '4', '5', '6', '7', '8'};

    @Test
    public void display_boardCell_in_board() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        reversiBoard.displayTheBoard();
    }

    @Test
    public void recognise_empty_cell() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        assertThat(reversiBoard.isSquareOnTheBoardEmpty(0, 0), is(true));
    }

    @Test
    public void recognise_illegal_cell_as_non_empty_cell() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        assertThat(reversiBoard.isSquareOnTheBoardEmpty(8, 8), is(false));
    }

    @Test
    public void recognise_non_empty_cell() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        assertThat(reversiBoard.isSquareOnTheBoardEmpty(4, 4), is(false));
    }

    @Test
    public void return_opposition_cell() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        assertThat(reversiBoard.identifyOppositionSquareOnTheBoard(SquareType.WHITE), is(SquareType.BLACK));
        assertThat(reversiBoard.identifyOppositionSquareOnTheBoard(SquareType.BLACK), is(SquareType.WHITE));
    }

    @Test
    public void detect_opposition_boardcell() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        reversiBoard.isAdjacentSquareTypeTheSame(SquareType.WHITE, 3, 2, DirectionOfCapture.N);
    }

    @Test
    public void find_candidate_cells() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        reversiBoard.listPotentialMoves(SquareType.WHITE);
        reversiBoard.displayPotentialNextMoves();

        for (PotentialNextMove x : reversiBoard.getPotentialNextMoves()) {
            System.out.println(x.toString());
            reversiBoard.setSquaresOnTheBoard(x.getXAxis(), x.getYAxis(), SquareType.SUGGESTED);
        }
        reversiBoard.displayTheBoard();
    }

    @Test
    public void move() {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        reversiBoard.listPotentialMoves(SquareType.WHITE);
        reversiBoard.displayPotentialNextMoves();
        for (PotentialNextMove potentialNextMove : reversiBoard.getPotentialNextMoves()) {
            System.out.println(potentialNextMove.toString());
            reversiBoard.doPotentialNextMove(SquareType.WHITE, potentialNextMove);
            break;
        }
        reversiBoard.displayTheBoard();
    }

    @Test
    public void
    produce_random_number() {

        int numberOfMoves = 5;
        for (int i = 0; i < 2000; i++) {
            int numericChoice = (int) (Math.round(Math.random() * (5 - 1)));
            assertThat(numericChoice, lessThan(numberOfMoves));
            assertThat(numericChoice, greaterThanOrEqualTo(0));
            //System.out.println(numericChoice);
        }
    }

    @Test
    public void retrieveCellRating() {
        assertThat(SquareRating.findRatingForSquare(0,0),is(9));
        assertThat(SquareRating.findRatingForSquare(0,1),is(4));
        assertThat(SquareRating.findRatingForSquare(7,7),is(9));
    }
}
