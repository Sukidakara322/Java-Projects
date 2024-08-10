import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;


public class Exercise_create extends JFrame {
    private JButton add_button;
    private JButton delete_button;
    private JButton edit_button;
    private JPanel mainPanel;
    private JPanel buttons;
    private JTable name_table;
    private JTextField exercise_name;
    private JButton go_to_training_create_button;
    private JButton go_to_progress_button;
    private JButton go_to_goals_create_button;
    private JButton go_to_training_session_button;
    private JPanel navigation;
    private Exercise_table_model tableModel;
    private JFrame mainFrame;
    public Exercise_create(String label, JFrame mainFrame){
        super(label);
        this.setSize(1200, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.mainFrame = mainFrame;
        tableModel = new Exercise_table_model();
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addExercise();
            }
        });
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editExercise();
            }
        });
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteExercise();
            }
        });
        get_Names_from_File();
        name_table.setModel(tableModel);
        go_to_training_create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_training_create();
            }
        });
        go_to_training_session_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_training_session();
            }
        });
        go_to_goals_create_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_goals_create();
            }
        });
        go_to_progress_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_progress();
            }
        });
    }
    private void go_to_progress(){
        Progress progress = new Progress("Progress", mainFrame);
        mainFrame.setContentPane(progress.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private void go_to_training_create(){
        Training_create training_create = new Training_create("Training Create", mainFrame);
        mainFrame.setContentPane(training_create.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private void go_to_training_session(){
        Training_session training_session = new Training_session("Training session", mainFrame);
        mainFrame.setContentPane(training_session.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private void go_to_goals_create(){
        Goal_create goals_create = new Goal_create("Goals Create", mainFrame);
        mainFrame.setContentPane(goals_create.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }

    private void addExercise(){
        String exercise = exercise_name.getText();
        if(!exercise.isEmpty()){
            tableModel.addExercise(exercise);
            exercise_name.setText("");
            save_Names_to_File();
        }else{
            JOptionPane.showMessageDialog(this, "Please write a name to add it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void editExercise(){
        Integer selected_row = name_table.getSelectedRow();
        if (selected_row != -1){
            String corrected_exercise = JOptionPane.showInputDialog(this, "Enter corrected exercise: ");
            if(corrected_exercise != null){
                tableModel.editExercise(selected_row, corrected_exercise);
                save_Names_to_File();
            }else{
                JOptionPane.showMessageDialog(this, "Please select a row to edit it", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

    }
    private void deleteExercise(){
        Integer selected_row = name_table.getSelectedRow();
        if (selected_row != -1){
            tableModel.deleteExercise(selected_row);
            save_Names_to_File();
        }else{
            JOptionPane.showMessageDialog(this, "Select a row to delete it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void save_Names_to_File(){
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("exercise_names.ser"))){
            oos.writeObject(tableModel.getExerciseNames());
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void get_Names_from_File(){
        try(ObjectInputStream ois = new ObjectInputStream(new FileInputStream("exercise_names.ser"))){
            Object object = ois.readObject();
            if(object instanceof ArrayList){
                tableModel.setExerciseNames((ArrayList<String>) object);
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private class Exercise_table_model extends AbstractTableModel {
        private ArrayList<String> exerciseNames = new ArrayList<>();
        private final String[] columnNames = {"Exercise Name"};

        @Override
        public int getRowCount() {
            return exerciseNames.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex == 0) {
                return exerciseNames.get(rowIndex);
            }
            return null;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }
        public void addExercise(String exercise) {
            exerciseNames.add(exercise);
            fireTableRowsInserted(exerciseNames.size() - 1, exerciseNames.size() - 1);
        }
        public void editExercise(int rowIndex, String exercise) {
            exerciseNames.set(rowIndex, exercise);
            fireTableCellUpdated(rowIndex, 0);
        }
        public void deleteExercise(int rowIndex) {
            exerciseNames.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
        public ArrayList<String> getExerciseNames() {
            return exerciseNames;
        }
        public void setExerciseNames(ArrayList<String> exerciseNames) {
            this.exerciseNames = exerciseNames;
            fireTableDataChanged();
        }
    }
}
