package Server;

import Client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameServer extends Remote {
    void login(Client client) throws RemoteException;
    void putInWaitingList (Client client) throws RemoteException;

    void disconect(int id) throws RemoteException;

    void stopSearching(int id) throws RemoteException;

    void makeMove(int x, int y, String mySymbol, int id, int gameSessionId) throws RemoteException;

    void endGameSession(int gameSessionId) throws RemoteException;
}
