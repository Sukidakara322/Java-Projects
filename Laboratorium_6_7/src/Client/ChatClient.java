package Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ChatClient {
    private Socket socket;
    private PrintWriter out;
    private GameClientGUI gui;
    private String clientId;

    public ChatClient(String serverAddress, int port, GameClientGUI gui, String clientId) throws Exception {
        this.gui = gui;
        this.clientId = clientId;
        socket = new Socket(serverAddress, port);
        out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        new Thread(() -> {
            try {
                String line;
                while ((line = in.readLine()) != null) {
                    gui.appendChatMessage(line);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void sendMessage(String message) {
        out.println(clientId + ": " + message);
    }
}
