import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;

public class TrainingSession implements Serializable {
    private static final long serialVersionUID = 1L;
    private Date date;
    private String name;
    private String parameters;


    public TrainingSession (Date date, String name, String parameters){
        this.date = date;
        this.name = name;
        this.parameters = parameters;
    }
    public Date getDate(){
        return date;
    }
    public void setDate(Date date){
        this.date = date;
    }
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getParameters(){
        return parameters;
    }
    public void setParameters(String parameters){
        this.parameters = parameters;
    }
    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.defaultWriteObject();
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
    }
    @Override
    public String toString(){
        return "TrainingSession{" + "date=" + date + ", name='" + name + '\'' + ", parameters='" + parameters + '\'' + '}';
    }
}
