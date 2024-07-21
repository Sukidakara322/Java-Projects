package Client;

import Server.GameSession;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Client extends Remote {
    void setId(int id) throws RemoteException;
    int getId() throws RemoteException;

    void disconnect() throws RemoteException;

    void setMoveOrder(boolean moveOrder) throws RemoteException;

    boolean getMoveOrder() throws RemoteException;

    void setShouldWait(boolean shouldWait) throws RemoteException;

    void setGameSessionId(int gameSessionId) throws RemoteException;

    void changeStatus() throws RemoteException;

    void playPressed() throws RemoteException;

    void makeMove(int x, int y, String mySymbol) throws RemoteException;

//    void setBoard(String[][] board) throws RemoteException;
//
//    String[][] getBoard() throws RemoteException;

    void setGamePage(GamePage gamePage) throws RemoteException;

    void stopSearch() throws RemoteException;

    void updateBoardAndMyTurn(String[][] newBoard, boolean moveOrder) throws RemoteException;

    void setGameSymbol(String gameSymbol) throws RemoteException;

    void winNotification() throws RemoteException;

    void loseNotification() throws RemoteException;

    void drawNotification() throws RemoteException;

}
