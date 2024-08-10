package WorkingWithoutFile;

public class SearchByPerson implements SearchBy<Decision> {
    private String NeededPerson;
    public SearchByPerson(String NeededPerson){
        this.NeededPerson = NeededPerson;
    }
    @Override
    public boolean matches(Decision obj) {
        return obj.getPerson().equals(NeededPerson);
    }
}
