package Server;

import java.io.Serializable;

public class MoveResult implements Serializable {
    private final boolean moveMade;
    private final boolean gameEnded;
    private final String winner;

    public MoveResult(boolean moveMade, boolean gameEnded, String winner) {
        this.moveMade = moveMade;
        this.gameEnded = gameEnded;
        this.winner = winner;
    }
    public boolean isMoveMade() {
        return moveMade;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public String getWinner() {
        return winner;
    }
}
