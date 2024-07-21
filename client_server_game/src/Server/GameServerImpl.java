package Server;

import Client.Client;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class GameServerImpl extends UnicastRemoteObject implements GameServer {
    private List<Client> clients;
    private List<Client> waitingList;

    private List<GameSession> activeGames;

    protected GameServerImpl() throws RemoteException {
        clients = new ArrayList<>();
        waitingList = new ArrayList<>();
        activeGames = new ArrayList<>();
    }

    @Override
    public void login(Client newClient) throws RemoteException {
        this.clients.add(newClient);
        this.clients.get(this.clients.indexOf(newClient)).setId(this.clients.indexOf(newClient));
        System.out.println("New client have just logged! Clients_Id:" + this.clients.indexOf(newClient));
    }

    @Override
    public void putInWaitingList(Client client) throws RemoteException {
        client.setShouldWait(true);
        this.waitingList.add(client);
        System.out.println("Client with Id: " + client.getId() + " enters waiting list");

        if (waitingList.size() > 1) {

            waitingList.get(0).setShouldWait(false);
            waitingList.get(1).setShouldWait(false);

            waitingList.get(0).changeStatus();
            waitingList.get(1).changeStatus();

            GameSession tmp = new GameSession(waitingList.get(0), waitingList.get(1));
            activeGames.add(tmp);
            activeGames.get(activeGames.size() - 1).setGameServer(this);
            activeGames.get(activeGames.size() - 1).setId(activeGames.size() - 1);


            waitingList.get(0).setGameSessionId(activeGames.size() - 1);
            waitingList.get(1).setGameSessionId(activeGames.size() - 1);

            System.out.println("Client(" + waitingList.get(0).getId() + ") and Client(" +
                    +waitingList.get(1).getId() + ") will play and have been removed from waiting list");

            System.out.println("New Game Session Id: " + (activeGames.size() - 1));

            waitingList.remove(0);
            waitingList.remove(0);
        }

    }

    @Override
    public void disconect(int id) throws RemoteException {
        this.clients.remove(id);

        for (Client client : clients) {
            client.setId(clients.indexOf(client));
        }

        stopSearching(id);

        System.out.println("Client.Client with Id:" + id + " have just disconnected");
    }

    @Override
    public void stopSearching(int id) throws RemoteException {
        int idInWaitingList;

        for (int i = 0; i < waitingList.size(); i++) {
            if (waitingList.get(i).getId() == id) {
                idInWaitingList = i;
                System.out.println("Client with Id: " + id + " quits waiting list");
                waitingList.remove(idInWaitingList);
                return;
            }
        }

        System.out.println("mamu ebal");
    }

    @Override
    public void makeMove(int x, int y, String mySymbol, int id, int gameSessionId) throws RemoteException {
        System.out.println("Game Session Id: " + gameSessionId);
        activeGames.get(gameSessionId).makeMove(x, y, mySymbol, id);
    }

    @Override
    public void endGameSession(int gameSessionId) throws RemoteException {
        activeGames.remove(gameSessionId);

        for (GameSession gameSession : activeGames) {
            gameSession.setId(activeGames.indexOf(gameSession));
            gameSession.getPlayer1().setGameSessionId(activeGames.indexOf(gameSession));
            gameSession.getPlayer2().setGameSessionId(activeGames.indexOf(gameSession));
        }


        System.out.println("Game Session with Id: " + gameSessionId + " have just ended");
    }


    public static void main(String[] args) {
        try {
            int port = 1500;
            LocateRegistry.createRegistry(port);
            GameServer server = new GameServerImpl();
            Naming.rebind("rmi://localhost:" + port + "/Server.GameServer", server);
            System.out.println("Server started at port: " + port);
        } catch (Exception e) {
            System.out.println("Server error: " + e.getMessage());
            e.printStackTrace();
        }
    }


}
