package WorkingWithoutFile;

public class SearchByComponent implements SearchBy<Decision> {
    private String NeededComponent;
    public SearchByComponent(String NeededComponent){
        this.NeededComponent = NeededComponent;
    }
    @Override
    public boolean matches(Decision obj) {
        return obj.getComponent().equals(NeededComponent);
    }
}
