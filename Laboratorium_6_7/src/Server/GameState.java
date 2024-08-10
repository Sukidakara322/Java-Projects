package Server;

import java.io.Serializable;

public class GameState implements Serializable {
    private final String[][] board;
    private final int moveCount;

    public GameState(String[][] board, int moveCount) {
        this.board = board;
        this.moveCount = moveCount;
    }

    public String[][] getBoard() {
        return board;
    }

    public int getMoveCount() {
        return moveCount;
    }
}
