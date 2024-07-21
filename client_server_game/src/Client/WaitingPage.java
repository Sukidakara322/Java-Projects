package Client;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class WaitingPage extends JFrame{
    private JPanel mainPanel;
    private JLabel waitingText;
    private JButton cancelButton;
    private Timer timer;

    private Client client;

    private int dotCount = 0;

    private boolean isStillWaiting;

    public boolean isStillWaiting() {
        return isStillWaiting;
    }

    public void setIsStillWaiting(boolean isStillWaiting) {
        this.isStillWaiting = isStillWaiting;
//        if (!isStillWaiting) {
//            stopWaiting();
//        }
    }

    public void stopWaiting() {
        if (timer != null) {
            timer.stop();
        }
        this.dispose(); // Закрыть окно
    }

    public WaitingPage(boolean isStillWaiting) {
        // Инициализация компонентов
        this.setContentPane(mainPanel);
        this.setSize(50, 40);
        this.setLocationRelativeTo(null);
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.isStillWaiting = isStillWaiting;

        // Инициализация и запуск таймера
//        initTimer();

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    client.stopSearch();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    dispose();
                }
            }
        });

        cancelButton.addActionListener(e -> {
            try {
                client.stopSearch();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                dispose();
            }
        });
    }

    public void initTimer() {
        timer = new Timer(500, e -> {
            updateWaitingText();
        });
        timer.start();
    }

    private void updateWaitingText() {
        if (!isStillWaiting) {
            stopWaiting();
            return;
        }

        // Обновляем текст метки, добавляя точки
        String baseText = "Searching for opponent";
        dotCount = (dotCount + 1) % 4; // Сбросить количество точек после трех
        StringBuilder text = new StringBuilder(baseText);
        for (int i = 0; i < dotCount; i++) {
            text.append(".");
        }
        waitingText.setText(text.toString());
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public void setClient(Client client) {
        this.client = client;
    }
//    public static void main(String[] args) throws InterruptedException {
////        isStillWaiting = Objects.equals(args[0], "1");
//        WaitingPage waitingPage = new WaitingPage();
//
//    }
}
