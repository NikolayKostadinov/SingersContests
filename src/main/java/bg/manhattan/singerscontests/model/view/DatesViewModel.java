package bg.manhattan.singerscontests.model.view;

import java.time.LocalDate;
import java.util.List;

public class DatesViewModel {
    private final List<LocalDate> dates;

    public DatesViewModel(List<LocalDate> dates) {
        this.dates = dates;
    }

    public List<LocalDate> getDates() {
        return dates;
    }
}
