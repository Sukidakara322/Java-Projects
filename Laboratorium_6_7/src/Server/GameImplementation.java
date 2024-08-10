package Server;

import Client.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GameImplementation extends UnicastRemoteObject implements GameInterface {
    private static final Logger logger = ServerLogger.getLogger();
    private final Map<String, TickTackToeGame> game_sessions;
    private final Map<String, String> player_session_map;
    private final List<String> waitingPlayers = new ArrayList<>();
    private final Map<String, ClientInterface> clients;
    private AtomicInteger playerIdCounter = new AtomicInteger(1);
    private final Map<String, String> pairingRequests = new ConcurrentHashMap<>();

    protected GameImplementation() throws RemoteException {
        super();
        game_sessions = new ConcurrentHashMap<>();
        player_session_map = new ConcurrentHashMap<>();
        clients = new ConcurrentHashMap<>();
    }
    @Override
    public void registerClient(ClientInterface client) throws RemoteException {
        if (client != null) {
            int playerId = playerIdCounter.getAndIncrement();
            clients.put(String.valueOf(playerId), client);
            waitingPlayers.add(String.valueOf(playerId));
            client.setPlayerId(String.valueOf(playerId));
            logger.info("Client registered with Player ID: " + playerId);
        } else {
            throw new RemoteException("Client registration failed.");
        }
    }
    public void checkAndPairPlayers() {
        synchronized (waitingPlayers) {
            if (waitingPlayers.size() >= 2) {
                String player1Id = waitingPlayers.remove(0);
                String player2Id = waitingPlayers.remove(0);

                sendPairingRequest(player1Id, player2Id);
                pairingRequests.put(player1Id, player2Id);
                pairingRequests.put(player2Id, player1Id);
            }
        }
    }
    private void sendPairingRequest(String player1Id, String player2Id) {
        try {
            ClientInterface client1 = clients.get(player1Id);
            ClientInterface client2 = clients.get(player2Id);

            if (client1 != null && client2 != null) {
                client1.requestPairingConfirmation(player2Id);
                client2.requestPairingConfirmation(player1Id);
            } else {
                if (client1 == null) {
                    waitingPlayers.add(player1Id);
                }
                if (client2 == null) {
                    waitingPlayers.add(player2Id);
                }
            }
        } catch (RemoteException e) {
            logger.log(Level.SEVERE, "Error sending pairing request", e);
            waitingPlayers.add(player1Id);
            waitingPlayers.add(player2Id);
        }
    }

    @Override
    public void confirmPairing(String playerId, boolean isConfirmed) throws RemoteException {
        String opponentId = pairingRequests.get(playerId);

        if (isConfirmed) {
            if (pairingRequests.containsKey(opponentId) && pairingRequests.get(opponentId).equals(playerId)) {
                startGameSession(playerId, opponentId);
                pairingRequests.remove(playerId);
                pairingRequests.remove(opponentId);
            }
        } else {
            waitingPlayers.add(playerId);
            waitingPlayers.add(opponentId);
            pairingRequests.remove(playerId);
            pairingRequests.remove(opponentId);
        }
    }
    private void updateGameState(String sessionID) {
        TickTackToeGame game = game_sessions.get(sessionID);
        if (game != null) {
            GameState gameState = new GameState(game.getBoard(), game.getMoveCount());
            broadcastGameState(sessionID, gameState);
            logger.info("Broadcasted game state for session " + sessionID);
        } else {
            logger.log(Level.SEVERE, "Failed to get game for session: " + sessionID);
        }
    }

    @Override
    public String findMatchForPlayer(String playerId) throws RemoteException {
        synchronized (waitingPlayers) {
            if (!waitingPlayers.isEmpty()) {
                for (String opponentId : waitingPlayers) {
                    if (!opponentId.equals(playerId)) {
                        waitingPlayers.remove(opponentId);
                        return opponentId;
                    }
                }
            }
            waitingPlayers.add(playerId);
        }
        return null;
    }

    @Override
    public void startGameSession(String player1Id, String player2Id) throws RemoteException {
        TickTackToeGame game = new TickTackToeGame();
        game.setPlayerIDs(player1Id, player2Id);

        String sessionId = generate_session_ID();
        game_sessions.put(sessionId, game);

        setClientSessionId(player1Id, sessionId, "X");
        setClientSessionId(player2Id, sessionId, "O");

        // Initial broadcast with an empty board
        GameState initialState = new GameState(game.getBoard(), 0);
        broadcastGameState(sessionId, initialState);
    }

    private void broadcastGameState(String sessionId, GameState gameState) {
        TickTackToeGame game = game_sessions.get(sessionId);
        if (game != null) {
            for (String playerId : game.getPlayerIDs()) {
                ClientInterface client = clients.get(playerId);
                if (client != null) {
                    try {
                        client.updateGame(gameState);
                    } catch (RemoteException e) {
                        logger.log(Level.SEVERE, "Error updating game state for player: " + playerId, e);
                    }
                }
            }
        }
    }
    @Override
    public void broadcastInitialState(String sessionId) throws RemoteException {
        TickTackToeGame game = game_sessions.get(sessionId);
        if (game != null) {
            GameState initialState = new GameState(game.getBoard(), 0);
            Set<String> playerIDs = game.getPlayerIDs();

            for (String playerID : playerIDs) {
                try {
                    ClientInterface client = clients.get(playerID);
                    if (client != null) {
                        client.updateGame(initialState);
                    } else {
                        logger.log(Level.WARNING, "Client not found for player ID: " + playerID);
                    }
                } catch (RemoteException e) {
                    logger.log(Level.SEVERE, "Error broadcasting initial game state to player " + playerID, e);
                }
            }
        } else {
            logger.log(Level.WARNING, "Game session not found for session ID: " + sessionId);
        }

    }

    @Override
    public void setClientSessionId(String playerId, String sessionId, String playerSymbol) throws RemoteException {
        ClientInterface client = clients.get(playerId);
        if (client != null) {
            client.setSessionId(sessionId);
            client.setPlayerSymbol(playerSymbol);
        } else {
            throw new RemoteException("Client not found for playerId: " + playerId);
        }
    }

    @Override
    public String pair_players(String player1_ID, String player2_ID) throws RemoteException {
        logger.info("Pairing players: " + player1_ID + " and " + player2_ID);
        String session_ID = generate_session_ID();
        TickTackToeGame game = new TickTackToeGame();
        game_sessions.put(session_ID, game);
        player_session_map.put(player1_ID, session_ID);
        player_session_map.put(player2_ID, session_ID);
        setClientSessionId(player1_ID, session_ID, "X");
        setClientSessionId(player2_ID, session_ID, "O");

        GameState initialState = new GameState(game.getBoard(), 0);
        broadcastGameState(session_ID, initialState);

        return session_ID;
    }

    @Override
    public String findRandomOpponent(String requestingPlayerId) throws RemoteException {
        waitingPlayers.remove(requestingPlayerId);
        if (!waitingPlayers.isEmpty()) {
            String opponentId = waitingPlayers.get(new Random().nextInt(waitingPlayers.size()));
            waitingPlayers.remove(opponentId);
            return opponentId;
        } else {
            waitingPlayers.add(requestingPlayerId);
            return null;
        }
    }

    @Override
    public boolean make_move(String session_ID, int x, int y, String player_symbol) throws RemoteException {
        System.out.println("Attempting move: Session ID = " + session_ID + ", Player Symbol = " + player_symbol);
        TickTackToeGame game = game_sessions.get(session_ID);
        if (game == null) {
            throw new RemoteException("Invalid session ID or game session not found.");
        }

        MoveResult result = game.make_move(x, y, player_symbol);

        if (result.isMoveMade()) {
            updateGameState(session_ID);

            if (result.isGameEnded()) {
                String winner = result.getWinner();
                broadcastGameEnd(session_ID, winner);
            }

            return true;
        }

        return false;
    }

    private void broadcastGameEnd(String sessionID, String winner) {
        game_sessions.get(sessionID).getPlayerIDs().forEach(playerID -> {
            try {
                ClientInterface client = clients.get(playerID);
                if (client != null) {
                    client.notifyGameEnd(winner);
                }
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error broadcasting game end to player: " + playerID, e);
            }
        });
    }

    @Override
    public boolean validate_move(String session_ID, int x, int y) throws RemoteException {
        TickTackToeGame game = game_sessions.get(session_ID);
        if (game == null) {
            throw new RemoteException("Session not found");
        }
        return game.validate_move(x, y);
    }

    private String generate_session_ID() {
        return UUID.randomUUID().toString();
    }
}
