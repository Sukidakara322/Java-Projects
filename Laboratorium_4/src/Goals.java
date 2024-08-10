import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class Goals implements Serializable {
    private static final long serialVersionUID = 1L;
    private String exercise_name;
    private String number_of_times;
    private String number_of_sets;
    private Date date_until;

    public Goals(String exercise_name, String number_of_times, String number_of_sets, Date date_until){
        this.exercise_name = exercise_name;
        this.number_of_times = number_of_times;
        this.number_of_sets = number_of_sets;
        this.date_until = date_until;
    }
    public String getExercise_name(){
        return exercise_name;
    }
    public void setExercise_name(String exercise_name){
        this.exercise_name = exercise_name;
    }
    public String getNumber_of_times() {
        return number_of_times;
    }
    public void setNumber_of_times(String number_of_times){
        this.number_of_times = number_of_times;
    }
    public String getNumber_of_sets(){
        return number_of_sets;
    }
    public void setNumber_of_sets(String number_of_sets){
        this.number_of_sets = number_of_sets;
    }
    public Date getDate_until(){
        return date_until;
    }
    public void setDate_until(Date date_until){
        this.date_until = date_until;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }
}
