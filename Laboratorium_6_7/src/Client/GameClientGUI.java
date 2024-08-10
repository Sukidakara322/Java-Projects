package Client;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.util.Objects;

public class GameClientGUI {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField chatInput;
    private JButton[][] gameButtons;
    private GameClient gameClient;
    private ChatClient chatClient;
    private JButton startGameButton;
    private static final int BOARD_SIZE = 3;
    private String serverAddress;
    private int chatPort;

    public GameClientGUI(String serverAddress, int chatPort) {
        this.serverAddress = serverAddress;
        this.chatPort = chatPort;

        frame = new JFrame("Tick-Tack-Toe Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        createGameBoard();
        createChatPanel();
        createStartGameButton();

        frame.pack();
        frame.setVisible(true);

        try {
            gameClient = new GameClient(serverAddress, this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getBoardSize() {
        return BOARD_SIZE;
    }
    public void enableGameBoard(boolean enable) {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameButtons[i][j].setEnabled(enable);
            }
        }
    }
    private void createStartGameButton() {
        startGameButton = new JButton("Start Game");
        startGameButton.addActionListener(e -> {
            gameClient.requestOpponent();
        });
        frame.add(startGameButton, BorderLayout.SOUTH);
    }
    private void createGameBoard() {
        JPanel gamePanel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        gameButtons = new JButton[BOARD_SIZE][BOARD_SIZE];

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                JButton button = new JButton();
                int finalI = i;
                int finalJ = j;
                button.addActionListener(e -> {
                    try {
                        gameClient.makeMove(finalI, finalJ);
                    } catch (RemoteException ex) {
                        throw new RuntimeException(ex);
                    }
                });
                gameButtons[i][j] = button;
                gamePanel.add(button);
            }
        }

        frame.add(gamePanel, BorderLayout.CENTER);
    }

    public void updateBoardCell(int x, int y, String symbol) {
        SwingUtilities.invokeLater(() -> {
            gameButtons[x][y].setText(symbol.equals("-") ? "" : symbol);
        });
    }
    public void clearBoard() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                gameButtons[i][j].setText("-");
            }
        }
    }

    private void createChatPanel() {
        chatArea = new JTextArea(10, 30);
        chatArea.setEditable(false);
        chatInput = new JTextField();
        chatInput.addActionListener(e -> {
            String message = chatInput.getText();
            chatClient.sendMessage(message);
            chatInput.setText("");
        });

        JPanel chatPanel = new JPanel(new BorderLayout());
        chatPanel.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatPanel.add(chatInput, BorderLayout.SOUTH);

        frame.add(chatPanel, BorderLayout.EAST);
    }
    public void startChatClient(String clientId) {
        try {
            chatClient = new ChatClient(serverAddress, chatPort, this, clientId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void appendChatMessage(String message) {
        SwingUtilities.invokeLater(() -> chatArea.append(message + "\n"));
    }

    public JFrame getFrame(){
        return frame;
    }
    public void setPlayerId(String playerId) {
        frame.setTitle("Tick-Tack-Toe Game - Player " + playerId);
    }
    public static void main(String[] args) {
        String serverAddress = args[0];
        int chatPort = Integer.parseInt(args[1]);
        SwingUtilities.invokeLater(() -> new GameClientGUI(serverAddress, chatPort));
    }
}

