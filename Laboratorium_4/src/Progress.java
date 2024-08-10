import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Progress extends JFrame {
    private JPanel mainPanel;
    private JPanel buttonsPanel;
    private JPanel graphPanel;
    private JLabel label1;
    private JComboBox exercise_selection;
    private JButton go_to_exercise_create_button;
    private JButton go_to_training_create_button;
    private JButton go_to_training_session_button;
    private JButton go_to_goals_button;
    private ArrayList<String> exercise_names;
    private ArrayList<TrainingSession> training_sessions_data;
    private ArrayList<Date> datesList;
    private ArrayList<String> parametersList;
    private ArrayList<Integer> firstOption;
    private ArrayList<String> secondOption;
    private ArrayList<Goals> goals_data;
    private static String goals_number_of_times;
    private static String goals_number_of_sets;
    private static Date goals_date_until;
    private JFrame mainFrame;

    public Progress(String label, JFrame mainFrame) {
        this.setSize(1200, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.pack();
        this.mainFrame = mainFrame;
        exercise_names = new ArrayList<>();
        training_sessions_data = new ArrayList<>();
        goals_data = new ArrayList<>();
        datesList = new ArrayList<>();
        parametersList = new ArrayList<>();
        firstOption = new ArrayList<>();
        secondOption = new ArrayList<>();

        exercise_selection.setModel(new DefaultComboBoxModel<>());

        get_names_of_exercises();
        exercise_combo_box();
        get_goals_data();

        exercise_selection.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateListsAndPrint();
            }
        });
        updateListsAndPrint();
        createOrUpdateChart();
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
        go_to_training_session_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_training_session();
            }
        });
        go_to_goals_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                go_to_goals_create();
            }
        });
    }
    private void go_to_exercise_create(){
        Exercise_create exercise_create = new Exercise_create("Exercise creator", mainFrame);
        mainFrame.setContentPane(exercise_create.getContentPane());
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

    private void createOrUpdateChart() {
        ChartPanel chartPanel = new ChartPanel();
        graphPanel.removeAll();
        graphPanel.add(chartPanel);
        graphPanel.revalidate();
        graphPanel.repaint();
        chartPanel.setData(firstOption, secondOption, datesList);
    }

    private void updateListsAndPrint() {
        get_training_sessions_data();
        get_goals_data();
        createOrUpdateChart();
    }

    private void get_goals_data() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("goals_data.ser"))) {
            Object object = ois.readObject();
            if (object instanceof ArrayList) {
                goals_data = (ArrayList<Goals>) object;
                String selectedExercise = (String) exercise_selection.getSelectedItem();
                boolean found = false;

                for (Goals goal : goals_data) {
                    if (goal.getExercise_name().contains(selectedExercise)) {
                        goals_number_of_times = goal.getNumber_of_times();
                        goals_number_of_sets = goal.getNumber_of_sets();
                        goals_date_until = goal.getDate_until();
                        found = true;
//                        System.out.println("Number of Times: " + goals_number_of_times);
//                        System.out.println("Number of Sets: " + goals_number_of_sets);
//                        System.out.println("Date Until: " + goals_date_until);
                        break;
                    }
                }

                if (!found) {
                    goals_number_of_times = null;
                    goals_number_of_sets = null;
                    goals_date_until = null;
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            goals_data = new ArrayList<>();
        }
    }

    private void get_training_sessions_data() {
        datesList.clear();
        parametersList.clear();
        training_sessions_data.clear();
        firstOption.clear();
        secondOption.clear();
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("training_session.ser"))) {
            Object object = ois.readObject();
            if (object instanceof ArrayList) {
                ArrayList<TrainingSession> trainingSessions = (ArrayList<TrainingSession>) object;

                String selectedExercise = (String) exercise_selection.getSelectedItem();

                for (TrainingSession session : trainingSessions) {
                    if (session.getParameters().contains(selectedExercise)) {
                        String regex = selectedExercise + "\\s+(\\d+)\\s*x\\s*([^,]+)";
                        Pattern pattern = Pattern.compile(regex);
                        Matcher matcher = pattern.matcher(session.getParameters());

                        while (matcher.find()) {
                            training_sessions_data.add(session);
                            datesList.add(session.getDate());
                            try {
                                int firstValue = Integer.parseInt(matcher.group(1));
                                firstOption.add(firstValue);
                            } catch (NumberFormatException | IllegalStateException e) {
                                firstOption.add(0);
                            }
                            try {
                                String second_option = matcher.group(2);
                                secondOption.add(second_option);
                            } catch (IllegalStateException e) {
                                secondOption.add("");
                            }
                            parametersList.add(session.getName() + " " + matcher.group());
                        }
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            training_sessions_data = new ArrayList<>();
        }
//        System.out.println("First Numeric Values: " + firstOption);
//        System.out.println("Second Options: " + secondOption);
//        System.out.println("Parameters List: " + parametersList);
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

    private static class ChartPanel extends JPanel {
        private List<Integer> firstOption;
        private List<String> secondOption;
        private List<Date> datesList;

        public void setData(List<Integer> firstOption, List<String> secondOption, List<Date> datesList) {
            this.firstOption = firstOption;
            this.secondOption = secondOption;
            this.datesList = datesList;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            Integer bottom_padding = 30;
            Integer x_beginning = bottom_padding;
            Integer y_beginning = getHeight() - bottom_padding;
            g2d.drawLine(x_beginning, y_beginning, getWidth() - bottom_padding, y_beginning);
            g2d.drawLine(x_beginning, y_beginning, x_beginning, bottom_padding);
            g2d.drawString("Sets", getWidth() - bottom_padding, y_beginning + bottom_padding / 2);
            g2d.drawString("Times", x_beginning - bottom_padding / 2, bottom_padding);
            drawBars(g2d);
        }

        private void drawBars(Graphics2D g2d) {
            if (firstOption == null || datesList == null || firstOption.size() != datesList.size()) {
                return;
            }
            Integer defaultWidth = 20;
            Integer starting_x = 50;

            for (int i = 0; i < firstOption.size(); i++) {
                Integer value = firstOption.get(i);
                Integer height = value * 5;
                Integer y = getHeight() - height - 30;
                Integer width;
                if (i < secondOption.size()) {
                    String secondOptionValue = secondOption.get(i);
                    try {
                        width = Integer.parseInt(secondOptionValue) * 7;
                        width = width > 0 ? width : defaultWidth;
                    } catch (NumberFormatException e) {
                        width = defaultWidth;
                    }
                } else {
                    width = defaultWidth;
                }
                g2d.fillRect(starting_x, y, width, height);
                drawGoalBars(g2d, starting_x, y, width, height);
                g2d.drawString(formatDate(datesList.get(i)), starting_x + width / 2, getHeight() - 10);  // Mark bar with date
                starting_x += width + 140;
            }
        }

        private void drawGoalBars(Graphics2D g2d, int x, int y, int width, int height) {
            if (goals_number_of_times != null && !goals_number_of_times.isEmpty() &&
                    goals_number_of_sets != null && !goals_number_of_sets.isEmpty()) {
                try {
                    Integer goals_value = Integer.parseInt(goals_number_of_times);
                    Integer goals_height = goals_value * 5;
                    Integer goals_y = getHeight() - goals_height - 30;
                    Integer goals_x = x + width + 45;

                    Integer goals_width;
                    try {
                        goals_width = Integer.parseInt(goals_number_of_sets) * 7;
                        goals_width = goals_width > 0 ? goals_width : 20;
                    } catch (NumberFormatException e) {
                        goals_width = 20;
                    }
                    g2d.setColor(Color.RED);
                    g2d.fillRect(goals_x, goals_y, goals_width, goals_height);
                    g2d.drawString(formatDate(goals_date_until), goals_x + goals_width / 2, getHeight() - 10);  // Mark goal bar with date
                    g2d.setColor(getForeground());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }

        private String formatDate(Date date) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            return sdf.format(date);
        }
    }
}
