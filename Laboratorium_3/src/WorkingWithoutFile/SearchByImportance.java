package WorkingWithoutFile;

public class SearchByImportance implements SearchBy<Decision>{
    private Integer NeededImportance;
    public SearchByImportance(Integer NeededImportance){
        this.NeededImportance = NeededImportance;
    }
    @Override
    public boolean matches(Decision obj) {
        return obj.getImportance().equals(NeededImportance);
    }
}
