package Client;

import Server.GameInterface;
import Server.GameState;

import javax.swing.*;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Objects;

public class GameClient extends UnicastRemoteObject implements ClientInterface {
    private GameInterface gameServer;
    private String playerId;
    private String sessionId;
    private final GameClientGUI gui;
    private String playerSymbol;

    public GameClient(String host, GameClientGUI gui) throws RemoteException {
        this.gui = gui;
        try {
            Registry registry = LocateRegistry.getRegistry(host, 1099);
            gameServer = (GameInterface) registry.lookup("GameServer");
            System.out.println("Connected to server");
            gameServer.registerClient(this);
            System.out.println("Client registered to server");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui.getFrame(), "Failed to connect to server: " + e.getMessage());
        }
    }

    @Override
    public void setPlayerId(String playerId) throws RemoteException {
        this.playerId = playerId;
        gui.setPlayerId(playerId);
        gui.startChatClient(playerId);
        System.out.println("Player ID set: " + playerId);
    }
    @Override
    public void requestPairingConfirmation(String opponentId) throws RemoteException {
        int response = JOptionPane.showConfirmDialog(gui.getFrame(),
                "Play against Player " + opponentId + "?", "Confirm Game",
                JOptionPane.YES_NO_OPTION);

        if (response == JOptionPane.YES_OPTION) {
            gameServer.confirmPairing(playerId, true);
        } else {
            gameServer.confirmPairing(playerId, false);
        }
    }
    @Override
    public void notifyGameEnd(String winner) {
        SwingUtilities.invokeLater(() -> {
            if (Objects.equals(winner, "-")) {
                JOptionPane.showMessageDialog(gui.getFrame(), "Game ended in a draw!");
            } else {
                JOptionPane.showMessageDialog(gui.getFrame(), "Game ended! Winner: " + winner);
            }
            resetGame();
        });
    }
    private void resetGame() {
        for (int i = 0; i < gui.getBoardSize(); i++) {
            for (int j = 0; j < gui.getBoardSize(); j++) {
                gui.updateBoardCell(i, j, "-");
            }
        }

        gui.clearBoard();
        gui.enableGameBoard(false);

        this.sessionId = null;

        int response = JOptionPane.showConfirmDialog(gui.getFrame(), "Start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);
        if (response == JOptionPane.YES_OPTION) {
            requestOpponent();
        }
    }
    public void requestOpponent() {
        try {
            String opponentId = gameServer.findRandomOpponent(playerId);
            if (opponentId != null) {
                int response = JOptionPane.showConfirmDialog(gui.getFrame(),
                        "Start game with " + opponentId + "?", "Confirm Opponent",
                        JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    startGameSession(opponentId);
                } else {
                    JOptionPane.showMessageDialog(gui.getFrame(), "Game not started. Returning to main menu.");
                }
            } else {
                JOptionPane.showMessageDialog(gui.getFrame(), "No opponents available at the moment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui.getFrame(),
                    "Failed to find opponent: " + e.getMessage());
        }
    }
    @Override
    public void handleGameEnd(String winner) {
        SwingUtilities.invokeLater(() -> {
            if (Objects.equals(winner, "-")) {
                JOptionPane.showMessageDialog(gui.getFrame(), "Game Over: It's a draw!");
            } else {
                JOptionPane.showMessageDialog(gui.getFrame(), "Game Over: Player " + winner + " wins!");
            }
            gui.enableGameBoard(false);
        });
    }
    @Override
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
        System.out.println("Session ID set: " + sessionId);
    }
    public void startGameSession(String opponentId) {
        try {
            this.sessionId = gameServer.pair_players(playerId, opponentId);
            System.out.println("Game session started: Player ID = " + playerId + ", Session ID = " + sessionId);

            gameServer.broadcastInitialState(sessionId);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(gui.getFrame(), "Failed to start game session: " + e.getMessage());
        }
    }
    @Override
    public void setPlayerSymbol(String symbol) throws RemoteException {
        this.playerSymbol = symbol;
        System.out.println("Player symbol set to: " + symbol);
        if ("O".equals(playerSymbol)) {
            gui.enableGameBoard(false);
        }
    }
    @Override
    public void updateGame(GameState gameState) {
        String[][] board = gameState.getBoard();
        int moveCount = gameState.getMoveCount();
        String currentPlayer = moveCount % 2 == 0 ? "X" : "O";

        SwingUtilities.invokeLater(() -> {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    gui.updateBoardCell(i, j, board[i][j]);
                }
            }
            gui.enableGameBoard(playerSymbol.equals(currentPlayer));
        });
        System.out.println("Game state updated. Current player: " + currentPlayer);
    }

    public void makeMove(int x, int y) throws RemoteException {
        System.out.println("Attempting move: Session ID = " + sessionId + ", Player ID = " + playerId);
        System.out.println("Player Symbol: " + playerSymbol);

        if (playerSymbol.equals("X") || playerSymbol.equals("O")) {
            boolean moveAccepted = gameServer.make_move(sessionId, x, y, playerSymbol);
            if (moveAccepted) {
                gui.updateBoardCell(x, y, playerSymbol);
                System.out.println("Move made at [" + x + "," + y + "] by player " + playerSymbol);
            } else {
                JOptionPane.showMessageDialog(gui.getFrame(), "Move rejected. It's not your turn or cell is occupied.");
            }
        } else {
            JOptionPane.showMessageDialog(gui.getFrame(), "It's not your turn.");
        }
    }
}