package game.of.life;

public class GameOfLifeTaskResult {
    private final boolean[][] changes;
    private final int start_row;

    public GameOfLifeTaskResult(boolean[][] changes, int start_row){
        this.changes = changes;
        this.start_row = start_row;
    }
    public boolean[][] getChanges(){
        return changes;
    }
    public int getStartRow(){
        return start_row;
    }
}
