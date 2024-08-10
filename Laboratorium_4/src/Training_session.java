import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;

public class Training_session extends JFrame{
    private JPanel mainPanel;
    private JPanel buttons;
    private JButton add_button;
    private JButton edit_button;
    private JButton delete_button;
    private JPanel parameters;
    private JLabel label1;
    private JLabel label2;
    private JComboBox training_name_box;
    private JLabel label3;
    private JTextField parameters_of_exercises;
    private JButton go_to_exercise_create_button;
    private JButton go_to_training_create_button;
    private JButton go_to_goals_create_button;
    private JButton go_to_progress_button;
    private JPanel date_chooser;
    private JPanel navigation;
    private JTable table;
    private JButton show_data_button;
    JDateChooser dateChooser = new JDateChooser();
    private ArrayList<Training> trainingData;
    private ArrayList<TrainingSession> training_sessionData;
    private Training_session_tableModel tableModel;
    private JFrame mainFrame;

    public Training_session (String label, JFrame mainFrame) {
        super(label);
        this.setSize(1200,300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.mainFrame = mainFrame;
        trainingData = new ArrayList<>();
        training_sessionData = new ArrayList<>();
        tableModel = new Training_session_tableModel(training_sessionData);
        training_name_box.setModel(new DefaultComboBoxModel<>());
        table.setModel(tableModel);
        date_chooser.add(dateChooser);
        get_training_data();
        training_name_box();
        get_training_session_data();
        Update_table();
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTraining_session();
            }
        });
        show_data_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showTraining_session();
            }
        });
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTraining_session();
            }
        });
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTraining_session();
            }
        });
        go_to_exercise_create_button.addActionListener(new ActionListener() {
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
    private void go_to_goals_create(){
        Goal_create goals_create = new Goal_create("Goals Create", mainFrame);
        mainFrame.setContentPane(goals_create.getContentPane());
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
    private void addTraining_session(){
        Date selected_date = dateChooser.getDate();
        String selected_training = (String) training_name_box.getSelectedItem();
        String done_parameters_of_exercises = parameters_of_exercises.getText();
        if (selected_date == null || selected_training == null || done_parameters_of_exercises == null) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        TrainingSession trainingSession = new TrainingSession(selected_date, selected_training, done_parameters_of_exercises);
        training_sessionData.add(trainingSession);
        save_training_session_data();
        String message = "Date: " + selected_date + "\n" + "Selected training: " + selected_training
                + "\n" + "Completed parameters of exercises: " + done_parameters_of_exercises;
        JOptionPane.showMessageDialog(this, message, "Training session added", JOptionPane.INFORMATION_MESSAGE);
        Update_table();
        dateChooser.setDate(null);
        training_name_box.setSelectedIndex(-1);
        parameters_of_exercises.setText("");
    }
    private void editTraining_session(){
        Integer selected_row = table.getSelectedRow();
        if(selected_row != -1){
            TrainingSession selected_training_session = training_sessionData.get(selected_row);
            JDateChooser dateChooser = new JDateChooser(selected_training_session.getDate());
            Integer option = JOptionPane.showConfirmDialog(this, dateChooser, "Enter correct date:", JOptionPane.OK_CANCEL_OPTION);
            if(option == JOptionPane.OK_OPTION){
                Date edited_date = dateChooser.getDate();
                if(edited_date != null){
                    String edited_name = (String) JOptionPane.showInputDialog(this, "Select correct training name: ",
                            "Edit name", JOptionPane.QUESTION_MESSAGE, null, getUniqueTrainingNames().toArray(), selected_training_session.getName());
                    if (edited_name != null) {
                        String edited_parameters = JOptionPane.showInputDialog(this, "Enter correct parameters of training: ", selected_training_session.getParameters());
                        if (edited_parameters != null) {
                            selected_training_session.setDate(edited_date);
                            selected_training_session.setName(edited_name);
                            selected_training_session.setParameters(edited_parameters);
                            save_training_session_data();
                            Update_table();
                        }
                    }
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to edit it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void deleteTraining_session(){
        Integer selected_row = table.getSelectedRow();
        if(selected_row != -1){
            tableModel.removeRow(selected_row);
            training_sessionData = tableModel.getTrainingSessionsData();
            save_training_session_data();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void Update_table() {
        tableModel.setTrainingSessionsData(training_sessionData);
    }
    private void save_training_session_data(){
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("training_session.ser"))){
            oos.writeObject(training_sessionData);
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void showTraining_session() {
        String selected_training_name = (String) training_name_box.getSelectedItem();
        if (selected_training_name != null) {
            List<Training> matching_trainings = find_trainings_by_name(selected_training_name);
            StringBuilder variantsStringBuilder = new StringBuilder();
            for (Training training : matching_trainings) {
                variantsStringBuilder.append(training.toString());
                variantsStringBuilder.append("\n");
            }
            parameters_of_exercises.setText(variantsStringBuilder.toString());
        }
    }
    private List<Training> find_trainings_by_name (String name){
        List<Training> matching_trainings = new ArrayList<>();
        for (Training training: trainingData){
            if(training.getName().equals(name)){
                matching_trainings.add(training);
            }
        }
        return matching_trainings;
    }

    private void training_name_box (){
        DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<>();
        Set<String> unique_training_names = new HashSet<>();
        for(Training training: trainingData){
            String training_name = training.getName();
            if(unique_training_names.add(training_name)){
                comboBoxModel.addElement(training_name);
            }
        }
        training_name_box.setModel(comboBoxModel);
    }
    private void get_training_data (){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("training_data.ser"))){
            Object object = ois.readObject();
            if(object instanceof ArrayList) {
                trainingData = (ArrayList<Training>) object;
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            trainingData = new ArrayList<>();
        }
    }
    private Set<String> getUniqueTrainingNames() {
        Set<String> uniqueTrainingNames = new HashSet<>();
        for (Training training : trainingData) {
            uniqueTrainingNames.add(training.getName());
        }
        return uniqueTrainingNames;
    }
    private void get_training_session_data(){
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("training_session.ser"))){
            Object object = ois.readObject();
            if (object instanceof ArrayList) {
                training_sessionData = (ArrayList<TrainingSession>) object;
            }
        } catch (IOException | ClassNotFoundException e){
            e.printStackTrace();
            training_sessionData = new ArrayList<>();
        }
    }

    private class Training_session_tableModel extends AbstractTableModel {
        private ArrayList<TrainingSession> trainingSessionsData;
        private final String[] columnNames = {"Date", "Name", "Done parameters"};
        public Training_session_tableModel(ArrayList<TrainingSession> trainingSessionsData){
            this.trainingSessionsData = trainingSessionsData;
        }
        @Override
        public int getRowCount(){
            return trainingSessionsData.size();
        }
        @Override
        public int getColumnCount(){
            return columnNames.length;
        }
        @Override
        public Object getValueAt (int rowIndex, int columnIndex){
            TrainingSession trainingSession = trainingSessionsData.get(rowIndex);
            switch (columnIndex){
                case 0:
                    return trainingSession.getDate();
                case 1:
                    return trainingSession.getName();
                case 2:
                    return trainingSession.getParameters();
                default:
                    return null;
            }
        }
        @Override
        public String getColumnName(int column){
            return columnNames[column];
        }
        public void setTrainingSessionsData(ArrayList<TrainingSession> trainingSessionsData){
            this.trainingSessionsData = trainingSessionsData;
            fireTableDataChanged();
        }
        public void removeRow(int rowIndex){
            trainingSessionsData.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }
        public ArrayList<TrainingSession> getTrainingSessionsData(){
            return trainingSessionsData;
        }
    }
}
