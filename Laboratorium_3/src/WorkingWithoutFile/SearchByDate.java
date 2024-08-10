package WorkingWithoutFile;

import java.time.LocalDate;

public class SearchByDate implements SearchBy<Decision> {
    private LocalDate NeededDate;
    public SearchByDate(LocalDate NeededDate){
        this.NeededDate = NeededDate;
    }

    @Override
    public boolean matches(Decision obj) {
        return obj.getDate().equals(NeededDate);
    }
}
