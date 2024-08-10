package Server;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class TickTackToeGame {
    private static final int size = 3;
    private String[][] board = new String[size][size];
    private int moveCount = 0;
    private String winner = "-";
    private boolean game_ended = false;
    private Set<String> playerIDs = new HashSet<>();

    public TickTackToeGame(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                board[i][j] = "-";
            }
        }
        resetGame();
    }
    public MoveResult make_move(int x, int y, String playerSymbol) {
        String currentPlayer = moveCount % 2 == 0 ? "X" : "O";
        if (x < 0 || x >= size || y < 0 || y >= size || !board[x][y].equals("-") || !playerSymbol.equals(currentPlayer)) {
            return new MoveResult(false, false, winner);
        }

        board[x][y] = playerSymbol;
        check_game_status();

        moveCount++;

        return new MoveResult(true, game_ended, winner);
    }
    public void resetGame() {
        moveCount = 0;
        game_ended = false;
    }
    private boolean checkForWin(){
        return checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin();
    }

    private boolean checkRowsForWin(){
        for (String[] strings : board) {
            if (!Objects.equals(strings[0], "-") && Objects.equals(strings[0], strings[1]) && Objects.equals(strings[1], strings[2])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkColumnsForWin(){
        for(int i = 0; i < board.length; i++){
            if(!Objects.equals(board[0][i], "-") && Objects.equals(board[0][i], board[1][i]) && Objects.equals(board[1][i], board[2][i])){
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin(){
        return (!Objects.equals(board[0][0], "-") && Objects.equals(board[0][0], board[1][1]) && Objects.equals(board[1][1], board[2][2])) ||
                (!Objects.equals(board[0][2], "-") && Objects.equals(board[0][2], board[1][1]) && Objects.equals(board[1][1], board[2][0]));
    }

    private boolean isBoardFull(){
        for(String[] row : board){
            for(String cell : row){
                if(Objects.equals(cell, "-")){
                    return false;
                }
            }
        }
        return true;
    }
    public void setPlayerIDs(String player1ID, String player2ID) {
        playerIDs.clear();
        playerIDs.add(player1ID);
        playerIDs.add(player2ID);
    }
    public Set<String> getPlayerIDs() {
        return playerIDs;
    }
    public boolean validate_move(int x, int y){
        return x >= 0 && x < size && y >= 0 && y < size && Objects.equals(board[x][y], "-");
    }
    private void check_game_status(){
        if (check_for_win()) {
            winner = moveCount % 2 != 0 ? "X" : "O";
            game_ended = true;
            return;
        }
        if (is_board_full()) {
            winner = "-";
            game_ended = true;
        }
        moveCount++;
    }

    public String getCurrentPlayer() {
        return moveCount % 2 == 0 ? "X" : "O";
    }
    public String[][] getBoard() {
        String[][] boardCopy = new String[board.length][];
        for (int i = 0; i < board.length; i++) {
            boardCopy[i] = board[i].clone();
        }
        return boardCopy;
    }
    private boolean check_for_win(){
        return check_rows_for_win() || check_columns_for_win() || check_diagonals_for_win();
    }
    private boolean check_rows_for_win(){
        for(int i = 0; i < size; i++){
            if(!Objects.equals(board[i][0], "-") && Objects.equals(board[i][0], board[i][1]) && Objects.equals(board[i][1], board[i][2])){
                return true;
            }
        }
        return false;
    }
    private boolean check_columns_for_win(){
        for(int i = 0; i < size; i++){
            if(!Objects.equals(board[0][i], "-") && Objects.equals(board[0][i], board[1][i]) && Objects.equals(board[1][i], board[2][i])){
                return true;
            }
        }
        return false;
    }
    private boolean check_diagonals_for_win(){
        return (!Objects.equals(board[0][0], "-") && Objects.equals(board[0][0], board[1][1]) && Objects.equals(board[1][1], board[2][2])) ||
                (!Objects.equals(board[0][2], "-") && Objects.equals(board[0][2], board[1][1]) && Objects.equals(board[1][1], board[2][0]));
    }
    private boolean is_board_full(){
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if (Objects.equals(board[i][j], "-")){
                    return false;
                }
            }
        }
        return true;
    }
    public String getWinner(){
        return winner;
    }
    public boolean isGame_ended(){
        return game_ended;
    }

    public int getMoveCount() {
        return moveCount;
    }

//    public void setCurrentPlayer(String currentPlayer) {
//        this.current_player = currentPlayer;
//    }
}
