package WorkingWithoutFile;

import java.io.Serializable;
import java.time.LocalDate;

public class Decision implements Serializable {
    private static final long serialVersionUID = 1L;
    LocalDate Date;
    String Component;
    String Person;
    Integer Importance;
    String Description;

    public Decision(LocalDate date, String component, String person, Integer importance, String message){
        this.Date = date;
        this.Component = component;
        this.Person = person;
        this.Importance = importance;
        this.Description = message;
    }
    public String getPerson(){
        return Person;
    }
    public LocalDate getDate(){
        return Date;
    }
    public String getComponent(){
        return Component;
    }
    public Integer getImportance(){
        return Importance;
    }
    @Override
    public String toString(){
        return "Date: " + Date +"\n" + "Component: " + Component + "\n" + "Person: " + Person + "\n" +
                "Importance(1 - 3): " + Importance + "\n" + "Description: " + Description + "\n";
    }
}
