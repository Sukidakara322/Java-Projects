import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

public class Training_create extends JFrame {
    private JPanel mainPanel;
    private JButton add_button;
    private JButton delete_button;
    private JButton edit_Button;
    private JPanel buttons;
    private JTable table;
    private JLabel label1;
    private JTextField training_name;
    private JLabel label2;
    private JComboBox<String> exercise_selection;
    private JLabel label3;
    private JTextField number_of_times;
    private JLabel label4;
    private JTextField number_of_sets;
    private JButton exercise_creator_button;
    private JButton go_to_progress_button;
    private JButton go_to_goals_create_button;
    private JButton training_session_button;
    private JPanel parameters;
    private JPanel navigation;
    private ArrayList<String> exercise_names;
    private ArrayList<Training> training_data;
    private TrainingTableModel tableModel;
    private JFrame mainFrame;

    public Training_create(String label, JFrame mainFrame) {
        super(label);
        this.setSize(1200, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.mainFrame = mainFrame;
        exercise_names = new ArrayList<>();
        training_data = new ArrayList<>();
        tableModel = new TrainingTableModel(training_data);
        exercise_selection.setModel(new DefaultComboBoxModel<>());
        table.setModel(tableModel);
        get_names_of_exercises();
        exercise_combo_box();
        get_training_data();
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTraining();
            }
        });
        edit_Button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTraining();
            }
        });
        delete_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTraining();
            }
        });
        Update_table();
        exercise_creator_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_exercise_create();
            }
        });
        training_session_button.addActionListener(new ActionListener() {
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
    private void go_to_exercise_create(){
        Exercise_create exercise_create = new Exercise_create("Exercise creator", mainFrame);
        mainFrame.setContentPane(exercise_create.getContentPane());
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

    private void addTraining() {
        String name_of_training = training_name.getText();
        String selected_exercise = (String) exercise_selection.getSelectedItem();
        String times_number = number_of_times.getText();
        String sets_number = number_of_sets.getText();
        Training training = new Training(name_of_training, selected_exercise, times_number, sets_number);
        training_data.add(training);
        save_training_data();
        String message = String.format("Name: %s%nExercise: %s%nTimes: %s%nSets: %s",
                name_of_training, selected_exercise, times_number, sets_number);
        JOptionPane.showMessageDialog(this, message, "Training Added", JOptionPane.INFORMATION_MESSAGE);
        Update_table();
        training_name.setText("");
        exercise_selection.setSelectedIndex(-1);
        number_of_times.setText("");
        number_of_sets.setText("");
    }

    private void editTraining() {
        Integer selected_row = table.getSelectedRow();
        if (selected_row != -1) {
            Training selected_training = training_data.get(selected_row);
            String edited_name = JOptionPane.showInputDialog(this, "Enter correct training name: ", selected_training.getName());
            if (edited_name != null) {
                String editedExercise = (String) JOptionPane.showInputDialog(this, "Select corrected exercise:",
                        "Edit Exercise", JOptionPane.QUESTION_MESSAGE, null, exercise_names.toArray(), selected_training.getExercise());
                String edited_times = JOptionPane.showInputDialog(this, "Enter corrected number of times: ", selected_training.getTimes());
                String edited_sets = JOptionPane.showInputDialog(this, "Enter corrected sets", selected_training.getSets());
                selected_training.setName(edited_name);
                selected_training.setExercise(editedExercise);
                selected_training.setTimes(edited_times);
                selected_training.setSets(edited_sets);
                save_training_data();
                Update_table();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to edit it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteTraining() {
        Integer selected_row = table.getSelectedRow();
        if (selected_row != -1) {
            tableModel.removeRow(selected_row);
            training_data = tableModel.getTrainingData();
            save_training_data();
        } else {
            JOptionPane.showMessageDialog(this, "Select a row to delete it", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void Update_table() {
        tableModel.setTrainingData(training_data);
    }

    private void get_training_data() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("training_data.ser"))) {
            Object object = ois.readObject();
            if (object instanceof ArrayList) {
                training_data = (ArrayList<Training>) object;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            training_data = new ArrayList<>();
        }
    }

    private void save_training_data() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("training_data.ser"))) {
            oos.writeObject(training_data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class TrainingTableModel extends AbstractTableModel {
        private ArrayList<Training> trainingData;
        private final String[] columnNames = {"Name", "Exercise", "Times", "Sets"};

        public TrainingTableModel(ArrayList<Training> trainingData) {
            this.trainingData = trainingData;
        }

        @Override
        public int getRowCount() {
            return trainingData.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Training training = trainingData.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return training.getName();
                case 1:
                    return training.getExercise();
                case 2:
                    return training.getTimes();
                case 3:
                    return training.getSets();
                default:
                    return null;
            }
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        public void setTrainingData(ArrayList<Training> trainingData) {
            this.trainingData = trainingData;
            fireTableDataChanged();
        }

        public void removeRow(int rowIndex) {
            trainingData.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);
        }

        public ArrayList<Training> getTrainingData() {
            return trainingData;
        }
    }
}
