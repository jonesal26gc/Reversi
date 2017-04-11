import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;

public class ReversiBoard {
    private static final char NEW_LINE = '\n';
    private static final char SPACE_CHARACTER = ' ';
    private static final char[] xAxisLabels = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
    private static final char[] yAxisLabels = {'1', '2', '3', '4', '5', '6', '7', '8'};
    private boolean autoplayMode = false;
    private SquareType[][] squaresOnTheBoard = new SquareType[8][8];
    private int countOfWhiteBoardCells = 0;
    private int countOfBlackBoardCells = 0;
    private ArrayList<PotentialNextMove> potentialNextMoves = new ArrayList<PotentialNextMove>();
    private boolean endOfGame = false;
    private boolean noMovesAvailable = false;
    private UserInput userInput = new UserInput();

    public ReversiBoard(Boolean autoplayMode) {
        this.autoplayMode = autoplayMode;
        initialiseTheBoard();
    }

    private void initialiseTheBoard() {
        for (int xAxis = 0; xAxis < squaresOnTheBoard.length; xAxis++) {
            for (int yAxis = 0; yAxis < squaresOnTheBoard[0].length; yAxis++) {
                setSquaresOnTheBoard(xAxis, yAxis, SquareType.EMPTY);
            }
        }
        setSquaresOnTheBoard(3, 4, SquareType.BLACK);
        setSquaresOnTheBoard(4, 3, SquareType.BLACK);
        setSquaresOnTheBoard(3, 3, SquareType.WHITE);
        setSquaresOnTheBoard(4, 4, SquareType.WHITE);
    }

    public void setSquaresOnTheBoard(int x, int y, SquareType squareType) {
        decrementCounterForPreviousPiece(x, y, squareType);
        this.squaresOnTheBoard[x][y] = squareType;
        incrementCounterForPiece(squareType);
    }

    private void decrementCounterForPreviousPiece(int x, int y, SquareType squareType) {
        if (squareType != SquareType.EMPTY) {
            switch (this.squaresOnTheBoard[x][y]) {
                case BLACK:
                    countOfBlackBoardCells--;
                    return;
                case WHITE:
                    countOfWhiteBoardCells--;
                    return;
            }
        }
    }

    private void incrementCounterForPiece(SquareType squareType) {
        switch (squareType) {
            case BLACK:
                countOfBlackBoardCells++;
                return;
            case WHITE:
                countOfWhiteBoardCells++;
                return;
        }
    }

    public boolean isEndOfGame() {
        return endOfGame;
    }

    public void setEndOfGame(boolean endOfGame) {
        this.endOfGame = endOfGame;
    }

    public void displayTheBoard() {
        System.out.print(NEW_LINE);
        for (int yAxis = squaresOnTheBoard[0].length - 1; yAxis >= 0; yAxis--) {
            System.out.print(String.valueOf(yAxisLabels[yAxis]) + "| ");
            for (int xAxis = 0; xAxis < squaresOnTheBoard.length; xAxis++) {
                System.out.print(squaresOnTheBoard[xAxis][yAxis].getIcon());
                System.out.print(SPACE_CHARACTER);
            }
            System.out.print(NEW_LINE);
        }
        System.out.print("   - - - - - - - -" + NEW_LINE);
        System.out.print("   ");
        for (int xAxis = 0; xAxis < squaresOnTheBoard.length; xAxis++) {
            System.out.print(String.valueOf(xAxisLabels[xAxis]) + SPACE_CHARACTER);
        }
        System.out.println(" (Score: White=" + countOfWhiteBoardCells + " / Black=" + countOfBlackBoardCells + "):");
        System.out.println();
    }

    public void playerMove(SquareType player) {
        System.out.println(player.name() + "'s move.");
        if (listPotentialMoves(player) > 0) {
            noMovesAvailable = false;
            PotentialNextMove potentialNextMove;
            if (autoplayMode & player == SquareType.BLACK) {
                potentialNextMove = automatedPlayerMove();
            } else {
                potentialNextMove = manualPlayerMoveWithUserInput(player);
            }
            if (potentialNextMove != null) {
                System.out.println(player.name() + " captured " + doPotentialNextMove(player, potentialNextMove) + " pieces.");
            }
        } else {
            System.out.println("No moves VALID moves available.");
            if (noMovesAvailable) {
                endOfGame = true;
                return;
            }
            noMovesAvailable = true;
        }
    }

    public int listPotentialMoves(SquareType squareType) {
        potentialNextMoves.clear();
        analyseAllSquaresOnTheBoard(squareType);
        Collections.sort(potentialNextMoves);
        return potentialNextMoves.size();
    }

    private PotentialNextMove automatedPlayerMove() {
        PotentialNextMove potentialNextMove;
        int numericChoice;
        int highestPreferenceRating = 0;
        int highestPreferenceRatingIndex = 0;
        for (int index = 0; index < potentialNextMoves.size(); index++) {
            if (potentialNextMoves.get(index).getPreferenceRating() > highestPreferenceRating) {
                highestPreferenceRating = potentialNextMoves.get(index).getPreferenceRating();
                highestPreferenceRatingIndex = index;
            }
        }
        if (highestPreferenceRatingIndex > 0) {
            numericChoice = highestPreferenceRatingIndex;
        } else {
            numericChoice = (int) (Math.round(Math.random() * (potentialNextMoves.size() - 1)));
        }
        int xAxis = getPotentialNextMoves().get(numericChoice).getXAxis();
        int yAxis = getPotentialNextMoves().get(numericChoice).getYAxis();
        String squareReference = String.valueOf(xAxisLabels).substring(xAxis, xAxis + 1)
                + String.valueOf(yAxisLabels).substring(yAxis, yAxis + 1);
        System.out.println("Move to " + squareReference + " was automatically chosen.");
        potentialNextMove = getPotentialNextMoves().get(numericChoice);
        return potentialNextMove;
    }

    private PotentialNextMove manualPlayerMoveWithUserInput(SquareType player) {
        displayPotentialNextMoves();
        PotentialNextMove potentialNextMove;
        while (true) {
            String squareReference = userInput.getUserInputString("Enter VALID co-ordinates for " + player.name() + "'s move").toUpperCase();
            if (squareReference.equals("X")) {
                endOfGame = true;
                return null;
            }
            potentialNextMove = checkMoveAgainstPotentialNextMoves(squareReference);
            if (potentialNextMove != null) {
                return potentialNextMove;
            }
        }
    }

    public int doPotentialNextMove(SquareType squareType, PotentialNextMove potentialNextMove) {
        setSquaresOnTheBoard(potentialNextMove.getXAxis(), potentialNextMove.getYAxis(), squareType);
        int score = 0;

        for (Map.Entry<DirectionOfCapture, Integer> x : potentialNextMove.getDirectionOfCaptureAndScores().entrySet()) {
            int xAxisAdj = potentialNextMove.getXAxis();
            int yAxisAdj = potentialNextMove.getYAxis();
            for (int i = 0; i < x.getValue(); i++) {
                xAxisAdj += x.getKey().getXAxisOffset();
                yAxisAdj += x.getKey().getYAxisOffset();
                setSquaresOnTheBoard(xAxisAdj, yAxisAdj, squareType);
                score++;
            }
        }
        if ((countOfBlackBoardCells + countOfWhiteBoardCells == 64)) {
            endOfGame = true;
        }
        return score;
    }

    private void analyseAllSquaresOnTheBoard(SquareType squareType) {
        for (int yAxis = squaresOnTheBoard.length - 1; yAxis >= 0; yAxis--) {
            for (int xAxis = 0; xAxis < squaresOnTheBoard.length; xAxis++) {
                if (isSquareOnTheBoardEmpty(xAxis, yAxis)) {
                    analyseEmptySquareOnTheBoardAsPotentialNextMove(squareType, yAxis, xAxis);
                }
            }
        }
    }

    public ArrayList<PotentialNextMove> getPotentialNextMoves() {
        return potentialNextMoves;
    }

    public void displayPotentialNextMoves() {
        if (potentialNextMoves.isEmpty()) {
            System.out.println("There are no moves for you.");
            return;
        }
        System.out.println("Potential moves are as follows:");
        int potentialMoveNumber = 0;
        for (PotentialNextMove potentialNextMove : potentialNextMoves) {
            potentialMoveNumber++;
            System.out.println(String.format(" %1$d) ", potentialMoveNumber) + String.valueOf(xAxisLabels[potentialNextMove.getXAxis()])
                    + String.valueOf(yAxisLabels[potentialNextMove.getYAxis()])
                    + " - capturing "
                    + potentialNextMove.getScore()
                    + " of your opponent's pieces.");
        }
    }

    public PotentialNextMove checkMoveAgainstPotentialNextMoves(String squareReference) {
        if (squareReference.length() != 2) {
            return null;
        }
        int xAxis = String.copyValueOf(xAxisLabels).indexOf(squareReference.charAt(0));
        int yAxis = String.copyValueOf(yAxisLabels).indexOf(squareReference.charAt(1));
        for (PotentialNextMove potentialNextMove : potentialNextMoves) {
            if (xAxis == potentialNextMove.getXAxis() & yAxis == potentialNextMove.getYAxis()) {
                return potentialNextMove;
            }
        }
        return null;
    }

    public boolean isSquareOnTheBoardEmpty(int xAxis, int yAxis) {
        try {
            return (squaresOnTheBoard[xAxis][yAxis] == SquareType.EMPTY);
        } catch (Exception ex) {
            return false;
        }
    }

    private void analyseEmptySquareOnTheBoardAsPotentialNextMove(SquareType squareType, int yAxis, int xAxis) {
        PotentialNextMove potentialNextMove = new PotentialNextMove(xAxis, yAxis);
        for (DirectionOfCapture directionOfCapture : DirectionOfCapture.values()) {
            analyseEachDirectionFromEmptySquareOnTheBoard(squareType, yAxis, xAxis, potentialNextMove, directionOfCapture);
        }
        if (potentialNextMove.getDirectionOfCaptureAndScoresSize() > 0) {
            potentialNextMoves.add(potentialNextMove);
        }
    }

    private void analyseEachDirectionFromEmptySquareOnTheBoard(SquareType squareType, int yAxis, int xAxis, PotentialNextMove potentialNextMove, DirectionOfCapture directionOfCapture) {
        int xAxisAdj = xAxis;
        int yAxisAdj = yAxis;
        int score = 0;
        while (isAdjacentSquareTypeTheSame(identifyOppositionSquareOnTheBoard(squareType), xAxisAdj, yAxisAdj, directionOfCapture)) {
            score++;
            xAxisAdj += directionOfCapture.getXAxisOffset();
            yAxisAdj += directionOfCapture.getYAxisOffset();
        }
        if (score > 0) {
            if (isAdjacentSquareTypeTheSame(squareType, xAxisAdj, yAxisAdj, directionOfCapture)) {
                potentialNextMove.addDirectionOfCaptureAndScores(directionOfCapture, score);
                potentialNextMove.setScore(potentialNextMove.getScore() + score);
                potentialNextMove.setPreferenceRating(SquareRating.findRatingForSquare(xAxis, yAxis));
            }
        }
    }

    public boolean isAdjacentSquareTypeTheSame(SquareType squareType, int xAxis, int yAxis, DirectionOfCapture directionOfCapture) {
        try {
            return (squaresOnTheBoard
                    [xAxis + directionOfCapture.getXAxisOffset()]
                    [yAxis + directionOfCapture.getYAxisOffset()]
                    == squareType);
        } catch (Exception ex) {
            return false;
        }
    }

    public SquareType identifyOppositionSquareOnTheBoard(SquareType squareType) {
        if (squareType == SquareType.WHITE) {
            return SquareType.BLACK;
        }
        return SquareType.WHITE;
    }

    public void displayResult() {
        System.out.println(NEW_LINE + "*********************");
        if (countOfWhiteBoardCells > countOfBlackBoardCells) {
            System.out.println(SquareType.WHITE.name() + " was the winner.");
        } else {
            if (countOfWhiteBoardCells < countOfBlackBoardCells) {
                System.out.println(SquareType.BLACK.name() + " was the winner.");
            } else {
                System.out.println("The game was drawn.");
            }
        }
        System.out.println("*********************");
    }
}
