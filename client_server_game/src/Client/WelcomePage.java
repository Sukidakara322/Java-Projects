package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.rmi.RemoteException;

public class WelcomePage extends JFrame {

    private Client client;
    private JLabel nameLabel;
    private JPanel mainPanel;
    private JButton playButton;

    private boolean isStillWaiting = true;

    private WaitingPage waitingPage = new WaitingPage(true);

    public WelcomePage() {
        try {
            client = new ClientImpl(this);
        } catch (Exception e) {
            System.out.println("Client.Client error: " + e.getMessage());
            e.printStackTrace();
        }

        playButton.addActionListener(e -> {

//            waitingPage = new WaitingPage(true);
            if (waitingPage == null) {
//                waitingPage.setVisible(true);
                waitingPage = new WaitingPage(true);
                waitingPage.setIsStillWaiting(true);
//                waitingPage.initTimer();
            }
            waitingPage.initTimer();
            waitingPage.setContentPane(waitingPage.getMainPanel());
            // Получение размеров и позиции исходного окна
            int welcomePageWidth = this.getWidth();
            int welcomePageHeight = this.getHeight();
            Point parentLocation = this.getLocation();

// Расчет центрального положения для нового окна
            int waitingPageWidth = 200; // Ширина нового окна
            int waitingPageHeight = 100; // Высота нового окна
            int waitingPageX = parentLocation.x + (welcomePageWidth - waitingPageWidth) / 2;
            int waitingPageY = parentLocation.y + (welcomePageHeight - waitingPageHeight) / 2;

// Установка размера и положения нового окна
            waitingPage.setSize(200,100);
            waitingPage.setLocation(waitingPageX, waitingPageY);

// Отображение нового окна
//            childFrame.setVisible(true);
//            waitingPage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            waitingPage.setVisible(true);
            waitingPage.setClient(client);
//            while (true) {
//                if (!this.isStillWaiting) {
//                    waitingPage.setIsStillWaiting(false);
//                    break;
//                }
//            }
            try {
                client.playPressed();
            } catch (Exception ex) {
                System.out.println("(.)(.) - siski blyatb");
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    client.disconnect();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    dispose();
                }
            }
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public static void main(String[] args) {
        WelcomePage welcomePage = new WelcomePage();
        welcomePage.setContentPane(welcomePage.mainPanel);
        welcomePage.setSize(500,400);
        welcomePage.setLocationRelativeTo(null);
        welcomePage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomePage.setVisible(true);
    }

    public void setStillWaiting(boolean stillWaiting) {
        isStillWaiting = stillWaiting;
    }

    public void breakWaiting() throws RemoteException {
        waitingPage.setIsStillWaiting(false);
        waitingPage = null;
        GamePage gamePage = new GamePage(this, this.client);
//        this.client.setGamePage(gamePage);
        this.setContentPane(gamePage.getMainPanel());
        this.setVisible(true);
    }
}
