
public class ReversiGameApp {

    public static void main(String[] args) {
        ReversiBoard reversiBoard = new ReversiBoard(true);
        SquareType player = SquareType.BLACK;
        while (!reversiBoard.isEndOfGame()) {
            player = reversiBoard.identifyOppositionSquareOnTheBoard(player);
            reversiBoard.displayTheBoard();
            reversiBoard.playerMove(player);
        }
        reversiBoard.displayResult();
        reversiBoard.displayTheBoard();
    }
}
