package game.of.life;

import java.util.concurrent.Callable;

public class GameOfLifeTask implements Callable<GameOfLifeTaskResult> {
    private final boolean[][] grid;
    private final int start_row;
    private final int end_row;
    private final int threadID;

    public GameOfLifeTask(boolean[][] grid, int startRow, int endRow, int threadID) {
        this.grid = grid;
        this.start_row = startRow;
        this.end_row = endRow;
        this.threadID = threadID;
    }

    private int countLiveNeighbors(int row, int col) {
        int count = 0;
        int rows = grid.length;
        int cols = grid[0].length;
        int[] row_offsets = {-1, 0, 1};
        int[] col_offsets = {-1, 0, 1};

        for (int i : row_offsets) {
            for (int j : col_offsets) {
                if (i == 0 && j == 0) {
                    continue;
                }

                int new_row = (row + i + rows) % rows;
                int new_col = (col + j + cols) % cols;

                if (grid[new_row][new_col]) {
                    count++;
                }
            }
        }

        return count;
    }

    @Override
    public GameOfLifeTaskResult call() {
        boolean[][] changes = new boolean[end_row - start_row][grid[0].length];

        for (int i = start_row; i < end_row; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                int live_neighbors = countLiveNeighbors(i, j);

                if (grid[i][j]) {
                    if (live_neighbors == 2 || live_neighbors == 3) {
                        changes[i - start_row][j] = true;
                    } else {
                        changes[i - start_row][j] = false;
                    }
                } else {
                    if (live_neighbors == 3) {
                        changes[i - start_row][j] = true;
                    } else {
                        changes[i - start_row][j] = false;
                    }
                }
            }
        }

        return new GameOfLifeTaskResult(changes, start_row);
    }
    public Integer getThreadID(){
        return threadID;
    }
    public int getStartRow(){
        return start_row;
    }
    public int getEndRow(){
        return end_row;
    }
    public boolean[][] getGrid(){
        return grid;
    }

    public void printThreadInformation() {
        System.out.println("tid " + threadID + ": rows: " + start_row + ":" + end_row +
                " (" + (end_row - start_row) + ") cols: 0:" + (grid[0].length - 1) +
                " (" + grid[0].length + ")");
    }
}
