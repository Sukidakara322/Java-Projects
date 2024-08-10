package Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatServer {
    private static final Logger logger = ServerLogger.getLogger();
    private final int port;
    private ServerSocket server_socket;
    private final Map<Socket, PrintWriter> client_writers;
    private final ExecutorService pool;

    public ChatServer(int port){
        this.port = port;
        client_writers = Collections.synchronizedMap(new HashMap<>());
        pool = Executors.newCachedThreadPool();
    }
    public void start() throws IOException {
        server_socket = new ServerSocket(port);
        logger.info("Chat server is running on port: " + port);
        try{
            while(true){
                Socket client_socket = server_socket.accept();
                PrintWriter writer = new PrintWriter(client_socket.getOutputStream(), true);
                client_writers.put(client_socket, writer);
                pool.execute(new ClientHandler(client_socket));
            }
        } finally {
            if(server_socket != null && !server_socket.isClosed()){
                server_socket.close();
            }
        }
    }
    private class ClientHandler implements Runnable{
        private final Socket client_socket;

        public ClientHandler(Socket socket){
            this.client_socket = socket;
        }

        @Override
        public void run() {
            logger.info("New client connected: " + client_socket);
            try (Scanner scanner = new Scanner(client_socket.getInputStream())){
                while(scanner.hasNextLine()){
                    String message = scanner.nextLine();
                    broadcast_message(message);
                }
            } catch (IOException e){
                logger.log(Level.SEVERE, "Client handler error", e);
            } finally {
                client_writers.remove(client_socket);
                try{
                    client_socket.close();
                } catch (IOException e){
                    logger.log(Level.SEVERE, "Error closing client socket", e);
                }
            }
            logger.info("Client disconnected: " + client_socket);
        }
        private void broadcast_message(String message){
            logger.info("Broadcasting message: " + message);
            for(PrintWriter writer : client_writers.values()){
                writer.println(message);
            }
        }
    }
}
