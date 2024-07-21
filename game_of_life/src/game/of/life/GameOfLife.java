package game.of.life;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class GameOfLife {
    public static void main(String[] args) {
        if(args.length != 2){
            System.err.println("Needed arguments: <filepath> <number_of_threads>");
            System.exit(1);
        }
        String file_path = args[0];

        try{
            Integer number_of_threads = Integer.parseInt(args[1]);
            if(number_of_threads <= 0){
                System.err.println("Please enter a positive number of threads");
                System.exit(1);
            }

        try{
            List<String> lines = readLinesFromFile(file_path);
            if(lines.size() < 4){
                System.err.println("Incorrect file format. Minimal correct input has 4 lines");
                System.exit(1);
            }

            Integer width = Integer.parseInt(lines.get(0));
            Integer length = Integer.parseInt(lines.get(1));
            Integer generations = Integer.parseInt(lines.get(2));
            Integer expected_alive_cells = Integer.parseInt(lines.get(3));

            if (width <= 0 || length <= 0 || generations <= 0 || expected_alive_cells < 0) {
                System.err.println("Invalid values for width, length, generations, or expected alive cells");
                System.exit(1);
            }

            List<Integer[]> alive_cell_coordinates = new ArrayList<>();
            for (int i = 4; i < lines.size(); i++) {
                String[] coordinates = lines.get(i).split(" ");
                if(coordinates.length == 2){
                    Integer row = Integer.parseInt(coordinates[0]);
                    Integer column = Integer.parseInt(coordinates[1]);
                    if (row < 0 || column < 0 || row >= width || column >= length) {
                        System.err.println("Invalid coordinate values at line " + (i + 1));
                        System.exit(1);
                    }
                    alive_cell_coordinates.add(new Integer[]{row, column});
                }else {
                    System.err.println("Invalid coordinate format at line " + (i + 1));
                    System.exit(1);
                }
            }

            Integer actual_alive_cells = alive_cell_coordinates.size();
            if(expected_alive_cells != actual_alive_cells){
                System.err.println("Mismatch between the expected alive cells and provided coordinates");
                System.exit(1);
            }

            ExecutorService executorService = Executors.newFixedThreadPool(number_of_threads);
            boolean[][] grid = initializeGrid(width, length, alive_cell_coordinates);
            boolean[][] nextGeneration = new boolean[width][length];

            System.out.println("Initial Generation:");
            printGrid(grid);

            List<GameOfLifeTask> tasks = new ArrayList<>();

            CountDownLatch latch = new CountDownLatch(generations);

            for (int generation = 1; generation <= generations; generation++) {
                tasks.clear();
                int rows_per_thread = width / ((ThreadPoolExecutor) executorService).getMaximumPoolSize();
                int extra_rows = width % ((ThreadPoolExecutor) executorService).getMaximumPoolSize();

                for (int i = 0; i < ((ThreadPoolExecutor) executorService).getMaximumPoolSize(); i++) {
                    int start_row = i * rows_per_thread + Math.min(i, extra_rows);
                    int end_row = (i + 1) * rows_per_thread + Math.min(i + 1, extra_rows);
                    tasks.add(new GameOfLifeTask(grid, start_row, end_row, i));
                }

                try {
                    List<Future<GameOfLifeTaskResult>> results = executorService.invokeAll(tasks);
                    for (Future<GameOfLifeTaskResult> result : results) {
                        GameOfLifeTaskResult task_result = result.get();
                        boolean[][] task_changes = task_result.getChanges();
                        int task_start_row = task_result.getStartRow();
                        for (int i = 0; i < task_changes.length; i++) {
                            System.arraycopy(task_changes[i], 0, nextGeneration[task_start_row + i], 0, task_changes[i].length);
                        }
                    }

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }

                System.out.println("\nGeneration " + generation + ":");
                printGrid(nextGeneration);
                System.arraycopy(nextGeneration, 0, grid, 0, width);
                latch.countDown();
            }

            try {
                latch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            printThreadInfo(tasks);
            executorService.shutdown();

            } catch (FileNotFoundException e){
                System.err.println("File not found : " + file_path);
                System.exit(1);
            }
        }catch (NumberFormatException e){
            System.err.println("Invalid format of number for number of threads");
            System.exit(1);
        }


    }

    private static List<String> readLinesFromFile(String file_path) throws FileNotFoundException{
        List<String> lines = new ArrayList<>();
        try(Scanner scanner = new Scanner(new File(file_path))){
            while(scanner.hasNextLine()){
                lines.add(scanner.nextLine());
            }
        }
        return lines;
    }
    private static void printThreadInfo(List<GameOfLifeTask> tasks) {
        System.out.println("# " + tasks.size() + " threads, row-based partitioning");
        List<Integer> unique_thread_IDs = tasks.stream()
                .map(GameOfLifeTask::getThreadID)
                .distinct()
                .sorted()
                .collect(Collectors.toList());

        for (Integer threadID : unique_thread_IDs) {
            List<GameOfLifeTask> tasks_for_thread = tasks.stream()
                    .filter(task -> task.getThreadID() == threadID)
                    .collect(Collectors.toList());

            System.out.println("tid " + threadID + ":");
            for (GameOfLifeTask task : tasks_for_thread) {
                int start_row = task.getStartRow();
                int end_row = task.getEndRow();
                System.out.print("rows: " + start_row + ":" + end_row +
                        " (" + (end_row - start_row) + ") cols: 0:" + (task.getGrid()[0].length - 1) +
                        " (" + task.getGrid()[0].length + ")\n");
            }
        }
    }

    private static boolean[][] initializeGrid(Integer width, Integer length, List<Integer[]> alive_cell_coordinates) {
        boolean[][] grid = new boolean[width][length];

        for (Integer[] coordinates : alive_cell_coordinates) {
            Integer row = coordinates[0];
            Integer column = coordinates[1];
            grid[row][column] = true;
        }

        return grid;
    }

    private static void printGrid(boolean[][] grid) {
        for (boolean[] row : grid) {
            for (boolean cell : row) {
                System.out.print((cell ? "X" : ".") + " ");
            }
            System.out.println();
        }
    }
}
