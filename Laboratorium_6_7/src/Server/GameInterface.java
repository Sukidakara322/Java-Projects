package Server;

import Client.ClientInterface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GameInterface extends Remote {

    void registerClient(ClientInterface client) throws RemoteException;

    void confirmPairing(String playerId, boolean isConfirmed) throws RemoteException;
    String pair_players(String player1_ID, String player2_ID) throws RemoteException;
    boolean make_move(String sessionID, int x, int y, String player_symbol) throws RemoteException;
    boolean validate_move(String sessionID ,int x, int y) throws RemoteException;
    String findRandomOpponent(String requestingPlayerId) throws RemoteException;

    void setClientSessionId(String playerId, String sessionId, String playerSymbol) throws RemoteException;
    String findMatchForPlayer(String playerId) throws RemoteException;
    void startGameSession(String player1Id, String player2Id) throws RemoteException;
    void broadcastInitialState(String sessionId) throws RemoteException;

}