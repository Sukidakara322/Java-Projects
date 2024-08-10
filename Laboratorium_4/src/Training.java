import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Training implements Serializable {
    private static final long serialVersionUID = 1L;
    private String name;
    private String exercise;
    private String times;
    private String sets;

    public Training(String name, String exercise, String times, String sets){
        this.name = name;
        this.exercise = exercise;
        this.times = times;
        this.sets = sets;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getExercise(){
        return exercise;
    }
    public void setExercise(String exercise){
        this.exercise = exercise;
    }
    public String getTimes(){
        return times;
    }
    public void setTimes(String times){
        this.times = times;
    }
    public String getSets(){
        return sets;
    }
    public void setSets(String sets){
        this.sets = sets;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }
    @Override
    public String toString(){
        return this.exercise + " " + this.times + " x " + this.sets + ", ";
    }
}
