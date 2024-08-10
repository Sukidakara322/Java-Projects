import javax.swing.*;
public class MainFrame {
    private static JFrame mainFrame;

    private MainFrame(){

    }
    public static JFrame getMainFrame(){
        if(mainFrame == null){
            mainFrame = new JFrame("MainFrame");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(1200, 300);
        }
        return mainFrame;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame mainFrame = getMainFrame();
            Training_create training_create_form = new Training_create("Training create", mainFrame);
            mainFrame.setContentPane(training_create_form.getContentPane());
            Training_session training_session_form = new Training_session("Training session", mainFrame);
            mainFrame.setContentPane(training_session_form.getContentPane());
            Goal_create goal_create_form = new Goal_create("Goal create", mainFrame);
            mainFrame.setContentPane(goal_create_form.getContentPane());
            Progress progress_form = new Progress("Progress", mainFrame);
            mainFrame.setContentPane(progress_form.getContentPane());
            Exercise_create exercise_create_form = new Exercise_create("Exercise create", mainFrame);
            mainFrame.setContentPane(exercise_create_form.getContentPane());
            mainFrame.setVisible(true);
        });
    }
}
