package Server;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Server {
    private static final Logger logger = ServerLogger.getLogger();
    private static final int chat_port = 12345;
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("GameServer", new GameImplementation());
            logger.info("RMI server is ready");
            Thread chatServerThread = new Thread(() -> {
                try {
                    new ChatServer(chat_port).start();
                } catch (IOException e) {
                    logger.log(Level.SEVERE, "Chat Server exception", e);
                }
            });
            chatServerThread.start();
            logger.info("Chat Server is running on port " + chat_port);

        } catch (Exception e){
            logger.log(Level.SEVERE, "Server exception", e);
        }
    }
}
