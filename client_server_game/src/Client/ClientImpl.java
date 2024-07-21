package Client;

import Client.Client;
import Server.GameServer;
import Server.GameSession;

import javax.swing.*;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ClientImpl extends UnicastRemoteObject implements Client {
    private int id;

    private String[][] board = new String[3][3];

    private GameServer server;

    private boolean shouldWait;

    private boolean order = false;

    private WelcomePage welcomePage;

    private GamePage gamePage;

    private int gameSessionId;
    @Override
    public void setId(int id) throws RemoteException {
        this.id = id;
    }

    protected ClientImpl(WelcomePage welcomePage) throws RemoteException, MalformedURLException, NotBoundException {
        super();
        this.welcomePage = welcomePage;
        int port = 1500;
        server = (GameServer) Naming.lookup("rmi://localhost:" + port + "/Server.GameServer");
        server.login(this);
        System.out.println("My Id: " + this.id);
    }
    public int getId() {
        return id;
    }

    @Override
    public void disconnect() throws RemoteException {
        server.disconect(this.id);
    }

    @Override
    public void setMoveOrder(boolean moveOrder) throws RemoteException {
        this.order = moveOrder;
    }

    @Override
    public boolean getMoveOrder() throws RemoteException {
        return this.order;
    }

    @Override
    public void setShouldWait(boolean shouldWait) throws RemoteException {
        this.shouldWait = shouldWait;
    }

    @Override
    public void setGameSessionId(int gameSessionId) throws RemoteException {
        this.gameSessionId = gameSessionId;
    }

    @Override
    public void changeStatus() throws RemoteException {
        this.welcomePage.breakWaiting();
        System.out.println(this.id + " " + this.shouldWait);
    }

    @Override
    public void playPressed() throws RemoteException {
        try {
            server.putInWaitingList(this);
        } catch (RemoteException e) {
            System.out.println("XDDDDDDDDDDD");
        }
    }

    @Override
    public void makeMove(int x, int y, String mySymbol) throws RemoteException {
        this.server.makeMove(x, y, mySymbol, this.id, this.gameSessionId);
    }

//    @Override
//    public void setBoard(String[][] board) throws RemoteException {
//        this.board = board;
//    }
//
//    @Override
//    public String[][] getBoard() throws RemoteException {
//        return board;
//    }

    @Override
    public void setGamePage(GamePage gamePage) throws RemoteException {
        this.gamePage = gamePage;
    }

    @Override
    public void stopSearch() throws RemoteException {
        server.stopSearching(this.id);
    }

    @Override
    public void updateBoardAndMyTurn(String[][] newBoard, boolean moveOrder) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            gamePage.updateBoardAndMyTurn(newBoard, moveOrder);
        });
    }

    @Override
    public void setGameSymbol(String gameSymbol) throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            gamePage.setMySymbol(gameSymbol);
        });
    }

    @Override
    public void winNotification() throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            gamePage.win();
        });
    }

    @Override
    public void loseNotification() throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            gamePage.lose();
        });
    }

    @Override
    public void drawNotification() throws RemoteException {
        SwingUtilities.invokeLater(() -> {
            gamePage.draw();
        });
    }


}
