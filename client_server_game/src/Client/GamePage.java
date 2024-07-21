package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.Arrays;

public class GamePage {

    private WelcomePage welcomePage;

    private Client client;
    private boolean isMyTurn = false;

    private String mySymbol;

    private String[][] board;
    private JPanel mainPanel;
    private JLabel gameInfo;
    private JPanel gamePanel;
    private JButton button00;
    private JButton button01;
    private JButton button02;
    private JButton button10;
    private JButton button11;
    private JButton button12;
    private JButton button20;
    private JButton button21;
    private JButton button22;
    private JList chatList;
    private JTextField messageField;
    private JScrollPane chatScrollPane;

    public GamePage(WelcomePage welcomePage, Client client) {
        this.client = client;
        this.welcomePage = welcomePage;

        try {
            this.client.setGamePage(this);
            if (this.client.getMoveOrder()) {
                this.isMyTurn = true;
            }

//            this.board = this.client.getBoard();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        if (!this.isMyTurn) {
            button00.setEnabled(false);
            button01.setEnabled(false);
            button02.setEnabled(false);
            button10.setEnabled(false);
            button11.setEnabled(false);
            button12.setEnabled(false);
            button20.setEnabled(false);
            button21.setEnabled(false);
            button22.setEnabled(false);
            gameInfo.setText("Opponent's turn");
        } else {
            button00.setEnabled(true);
            button01.setEnabled(true);
            button02.setEnabled(true);
            button10.setEnabled(true);
            button11.setEnabled(true);
            button12.setEnabled(true);
            button20.setEnabled(true);
            button21.setEnabled(true);
            button22.setEnabled(true);
            gameInfo.setText("Your turn");
        }

//        updateBoardAndMyTurn(this.board, this.isMyTurn);



        button00.addActionListener(e -> {
            submitMove(0, 0);
        });

        button01.addActionListener(e -> {
            submitMove(0, 1);
        });

        button02.addActionListener(e -> {
            submitMove(0, 2);
        });

        button10.addActionListener(e -> {
            submitMove(1, 0);
        });

        button11.addActionListener(e -> {
            submitMove(1, 1);
        });

        button12.addActionListener(e -> {
            submitMove(1, 2);
        });

        button20.addActionListener(e -> {
            submitMove(2, 0);
        });

        button21.addActionListener(e -> {
            submitMove(2, 1);
        });

        button22.addActionListener(e -> {
            submitMove(2, 2);
        });
    }
    public JPanel getMainPanel() {
        return mainPanel;
    }

    private void submitMove(int x, int y) {
        try {
            client.makeMove(x, y, mySymbol);
//            updateBoard();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateBoardAndMyTurn(String[][] newBoard, boolean isMyTurn) {
        this.board = new String[newBoard.length][];
        for (int i = 0; i < newBoard.length; i++) {
            this.board[i] = Arrays.copyOf(newBoard[i], newBoard[i].length);
        }

        this.isMyTurn = isMyTurn;

        button00.setText(this.board[0][0]);
        button01.setText(this.board[0][1]);
        button02.setText(this.board[0][2]);
        button10.setText(this.board[1][0]);
        button11.setText(this.board[1][1]);
        button12.setText(this.board[1][2]);
        button20.setText(this.board[2][0]);
        button21.setText(this.board[2][1]);
        button22.setText(this.board[2][2]);

        if (!this.isMyTurn) {
            button00.setEnabled(false);
            button01.setEnabled(false);
            button02.setEnabled(false);
            button10.setEnabled(false);
            button11.setEnabled(false);
            button12.setEnabled(false);
            button20.setEnabled(false);
            button21.setEnabled(false);
            button22.setEnabled(false);
            gameInfo.setText("Opponent's turn");
        } else {
            if (this.board[0][0].equals("Za")){
                button00.setEnabled(true);
            }
            if (this.board[0][1].equals("Za")){
                button01.setEnabled(true);
            }
            if (this.board[0][2].equals("Za")){
                button02.setEnabled(true);
            }
            if (this.board[1][0].equals("Za")){
                button10.setEnabled(true);
            }
            if (this.board[1][1].equals("Za")){
                button11.setEnabled(true);
            }
            if (this.board[1][2].equals("Za")){
                button12.setEnabled(true);
            }
            if (this.board[2][0].equals("Za")){
                button20.setEnabled(true);
            }
            if (this.board[2][1].equals("Za")){
                button21.setEnabled(true);
            }
            if (this.board[2][2].equals("Za")){
                button22.setEnabled(true);
            }

//            button01.setEnabled(true);
//            button02.setEnabled(true);
//            button10.setEnabled(true);
//            button11.setEnabled(true);
//            button12.setEnabled(true);
//            button20.setEnabled(true);
//            button21.setEnabled(true);
//            button22.setEnabled(true);
            gameInfo.setText("Your turn");
        }



//        welcomePage.setVisible(true);
    }

    public void win() {
        this.gameInfo.setText("You've won!!!");
        this.gamePanel.setBackground(Color.GREEN);
        this.mainPanel.setBackground(Color.GREEN);
//        this.setVisible(true);
        JOptionPane.showMessageDialog(this.getMainPanel(), "Congratulations", "You've won!!!", JOptionPane.OK_OPTION);
        this.welcomePage.setContentPane(this.welcomePage.getMainPanel());
        this.welcomePage.setVisible(true);
    }

    public void lose() {
        this.gameInfo.setText("You've lost");
        this.gamePanel.setBackground(Color.RED);
        this.mainPanel.setBackground(Color.RED);
        JOptionPane.showMessageDialog(this.getMainPanel(), "Don't give up! Better luck next time", "You've lost", JOptionPane.OK_OPTION);
        this.welcomePage.setContentPane(this.welcomePage.getMainPanel());
        this.welcomePage.setVisible(true);
    }

    public void draw() {
        this.gameInfo.setText("Draw");
        this.gamePanel.setBackground(Color.DARK_GRAY);
        this.mainPanel.setBackground(Color.DARK_GRAY);
        JOptionPane.showMessageDialog(this.getMainPanel(), "Both of you've played well", "Draw", JOptionPane.OK_OPTION);
        this.welcomePage.setContentPane(this.welcomePage.getMainPanel());
        this.welcomePage.setVisible(true);
    }

    public void setMySymbol(String mySymbol) {
        this.mySymbol = mySymbol;
    }
}
