import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class Goal_create extends JFrame{
    private JPanel mainPanel;
    private JPanel buttons;
    private JButton add_button;
    private JButton edit_button;
    private JButton delete_button;
    private JTable table;
    private JPanel parameters;
    private JLabel label1;
    private JComboBox exercise_selection;
    private JLabel label2;
    private JTextField number_of_times;
    private JLabel label3;
    private JTextField number_of_sets;
    private JLabel label4;
    private JPanel panel_for_date;
    private JPanel date_chooser;
    private JPanel navigation;
    private JButton go_to_exercise_creator_button;
    private JButton go_to_training_create_button;
    private JButton go_to_training_session_button;
    private JButton go_to_progress_button;
    JDateChooser dateChooser = new JDateChooser();
    private ArrayList<String> exercise_names;
    private ArrayList<Goals> goals_data;
    private GoalsTableModel tableModel;
    private JFrame mainFrame;
    public Goal_create(String label, JFrame mainFrame){
        this.setSize(1200,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.mainFrame = mainFrame;
        exercise_names = new ArrayList<>();
        goals_data = new ArrayList<>();
        tableModel = new GoalsTableModel(goals_data);
        exercise_selection.setModel(new DefaultComboBoxModel());
        table.setModel(tableModel);
        date_chooser.add(dateChooser);
        get_names_of_exercises();
        exercise_combo_box();
        get_goals_data();
        Update_table();
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGoal();
            }
        });
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editGoals();
            }
        });
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteGoals();
            }
        });
        go_to_exercise_creator_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_exercise_create();
            }
        });
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
    private void go_to_training_session(){
        Training_session training_session = new Training_session("Training session", mainFrame);
        mainFrame.setContentPane(training_session.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private void go_to_training_create(){
        Training_create training_create = new Training_create("Training Create", mainFrame);
        mainFrame.setContentPane(training_create.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private void go_to_exercise_create(){
        Exercise_create exercise_create = new Exercise_create("Exercise creator", mainFrame);
        mainFrame.setContentPane(exercise_create.getContentPane());
        mainFrame.revalidate();
        mainFrame.repaint();
    }
    private void addGoal(){
        String selected_exercise = (String) exercise_selection.getSelectedItem();
        String selected_number_of_times = number_of_times.getText();
        String selected_number_of_sets = number_of_sets.getText();
        Date selected_date_until = dateChooser.getDate();
        if (selected_exercise == null || selected_number_of_times== null || selected_number_of_sets == null || selected_date_until == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Goals goals = new Goals(selected_exercise, selected_number_of_times, selected_number_of_sets, selected_date_until);
        goals_data.add(goals);
        save_goals_data();
        String message = "Exercise: " + selected_exercise + "\n" + "Number of times/ time: " + selected_number_of_times
                + "\n" + "Number of sets/ unit: " + selected_number_of_sets + "\n" + "Date until: " + selected_date_until;
        JOptionPane.showMessageDialog(this, message, "Goal Added", JOptionPane.INFORMATION_MESSAGE);
        Update_table();
        dateChooser.setDate(null);
        exercise_selection.setSelectedIndex(-1);
        number_of_times.setText("");
        number_of_sets.setText("");
    }
    private void editGoals(){
        Integer selected_row = table.getSelectedRow();
        if(selected_row != -1){
            Goals selected_goal = goals_data.get(selected_row);
            String edited_exercise = (String) JOptionPane.showInputDialog(this, "Select correct exercise:",
                    "Edit Exercise", JOptionPane.QUESTION_MESSAGE, null, exercise_names.toArray(), selected_goal.getExercise_name());
                if(edited_exercise != null){
                    String edited_times = JOptionPane.showInputDialog(this, "Enter corrected number of times: ", selected_goal.getNumber_of_times());
                    if (edited_times != null) {
                        String edited_sets = JOptionPane.showInputDialog(this, "Enter corrected sets", selected_goal.getNumber_of_sets());
                        if (edited_sets != null) {
                            JDateChooser dateChooser = new JDateChooser(selected_goal.getDate_until());
                            Integer option = JOptionPane.showConfirmDialog(this, dateChooser, "Enter correct date:", JOptionPane.OK_CANCEL_OPTION);
                            if(option == JOptionPane.OK_OPTION){
                                Date edited_date = dateChooser.getDate();
                                if(edited_date != null){
                                    selected_goal.setExercise_name(edited_exercise);
                                    selected_goal.setNumber_of_times(edited_times);
                                    selected_goal.setNumber_of_sets(edited_sets);
                                    selected_goal.setDate_until(edited_date);
                                    save_goals_data();
                                    Update_table();
                                }
                            }
                        }
                    }
                }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to edit it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void deleteGoals(){
        Integer selected_row = table.getSelectedRow();
        if(selected_row != -1){
            tableModel.removeRow(selected_row);
            goals_data = tableModel.getGoalsData();
            save_goals_data();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Update_table() {
        tableModel.setGoalsData(goals_data);
    }
    private void get_goals_data() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("goals_data.ser"))) {
            Object object = ois.readObject();
            if (object instanceof ArrayList) {
                goals_data = (ArrayList<Goals>) object;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            goals_data = new ArrayList<>();
        }
    }
    private void save_goals_data() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("goals_data.ser"))) {
            oos.writeObject(goals_data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void get_names_of_exercises() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("exercise_names.ser"))) {
            Object object = ois.readObject();
            if (object instanceof ArrayList) {
                exercise_names = (ArrayList<String>) object;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            exercise_names = new ArrayList<>();
        }
    }
    private void exercise_combo_box() {
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        for (String exercise : exercise_names) {
            comboBoxModel.addElement(exercise);
        }
        exercise_selection.setModel(comboBoxModel);
    }
    private class GoalsTableModel extends AbstractTableModel{
        private ArrayList<Goals> goalsData;
        private final String[] columnNames = {"Exercise", "Times", "Sets", "Date until"};
        public GoalsTableModel (ArrayList<Goals> goalsData){
            this.goalsData = goalsData;
        }
        @Override
        public int getRowCount() {
            return goalsData.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Goals goals = goalsData.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return goals.getExercise_name();
                case 1:
                    return goals.getNumber_of_times();
                case 2:
                    return goals.getNumber_of_sets();
                case 3:
                    return goals.getDate_until();
                default:
                    return null;
            }
        }
        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void setGoalsData(ArrayList<Goals> goalsData) {
            this.goalsData = goalsData;
            fireTableDataChanged();
        }

        public void removeRow(int rowIndex) {
            goalsData.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public ArrayList<Goals> getGoalsData() {
            return goalsData;
        }
    }
}
