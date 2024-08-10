package Client;

import Server.GameState;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientInterface extends Remote {
    void updateGame(GameState gameState) throws RemoteException;
    void setSessionId(String sessionId) throws RemoteException;
    void handleGameEnd(String winner) throws RemoteException;
    void notifyGameEnd(String winner) throws RemoteException;
    void setPlayerId(String playerId) throws RemoteException;
    void requestPairingConfirmation(String opponentId) throws RemoteException;
    void setPlayerSymbol(String symbol) throws RemoteException;
}

