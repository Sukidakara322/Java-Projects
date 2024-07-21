package Server;

import Client.Client;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Random;

public class GameSession implements Serializable {
    private Client player1;
    private Client player2;

    private int id;

    private GameServer gameServer;

    private String[][] board = new String[3][];

    private int amountOfMoves = 0;

    public GameSession(Client player1, Client player2) {
        this.player1 = player1;
        this.player2 = player2;

        System.out.println("Start board:");

        for (int i = 0; i < board.length; i++) {
            board[i] = new String[3];
            for (int j = 0; j < board.length; j++) {
                board[i][j] = "Za";
            }
        }

        for (String[] strings: board) {
            for (String string: strings) {
                System.out.print(string);
            }
            System.out.println();
        }

        try {
//            this.player1.setGameSession(this);
//            this.player2.setGameSession(this);


            boolean player1Start = new Random().nextBoolean();
            if (player1Start) {
                this.player1.updateBoardAndMyTurn(board, true);
                player1.setGameSymbol("X");
                this.player2.updateBoardAndMyTurn(board, false);
                player2.setGameSymbol("0");
            } else {
                this.player1.updateBoardAndMyTurn(board, false);
                player1.setGameSymbol("0");
                this.player2.updateBoardAndMyTurn(board, true);
                player2.setGameSymbol("X");
            }
            System.out.println("baldezh");

            System.out.println("Player1:");
            System.out.println("Id: " + player1.getId());
            System.out.println("Move order: " + player1.getMoveOrder());
            System.out.println("Player2:");
            System.out.println("Id: " + player2.getId());
            System.out.println("Move order: " + player2.getMoveOrder());
        } catch (Exception e) {
            System.out.println("pizdec(((");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public void makeMove(int x, int y, String playerSymbol, int id) throws RemoteException {
        board[x][y] = playerSymbol;
        amountOfMoves++;
        System.out.println("Player with Id: " + id + " placed " + playerSymbol + " on board");
        System.out.println("Board (Online):");

        for (String[] strings: board) {
            for (String string : strings) {
                System.out.print(string);
            }
            System.out.println();
        }

        if (id == player1.getId()) {
            player1.updateBoardAndMyTurn(board, false);
            player2.updateBoardAndMyTurn(board, true);
        } else {
            player1.updateBoardAndMyTurn(board, true);
            player2.updateBoardAndMyTurn(board, false);
        }

        if (this.checkIfWins(playerSymbol)) {
            System.out.println("jopu sosi");
            player1.updateBoardAndMyTurn(board, false);
            player2.updateBoardAndMyTurn(board, false);

            if (id == player1.getId()) {
                player1.winNotification();
                player2.loseNotification();
            } else {
                player1.loseNotification();
                player2.winNotification();
            }

            gameServer.endGameSession(this.id);

        } else if (amountOfMoves == 9) {
            System.out.println("nichejnyj - kak Krym");
            player1.updateBoardAndMyTurn(board, false);
            player2.updateBoardAndMyTurn(board, false);
            player1.drawNotification();
            player2.drawNotification();

            gameServer.endGameSession(this.id);
        }

    }

    private boolean checkIfWins(String symbolToCheck) {
        int row;
        int[] columns = {0, 0, 0};
        int[] diagonals = {0, 0};

        for (int i = 0; i < board.length; i++) {
            row = 0;

            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j].equals(symbolToCheck)) {
                    row++;
                    columns[j]++;
                    if (i == j) {
                        diagonals[0]++;
                    }
                    if (i + j == 2) {
                        diagonals[1]++;
                    }

                }
            }

            if (row == 3) {
                return true;
            }
        }

        for (int column: columns) {
            if (column == 3) {
                return true;
            }
        }

        for (int diagonal: diagonals) {
            if (diagonal == 3) {
                return true;
            }
        }

        System.out.println("Gramy dalej");
        return false;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setGameServer(GameServer gameServer) {
        this.gameServer = gameServer;
    }

    public Client getPlayer1() {
        return player1;
    }

    public Client getPlayer2() {
        return player2;
    }
}
