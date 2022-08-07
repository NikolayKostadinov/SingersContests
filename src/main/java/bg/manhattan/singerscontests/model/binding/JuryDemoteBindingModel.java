package bg.manhattan.singerscontests.model.binding;

import java.util.ArrayList;
import java.util.List;

public class JuryDemoteBindingModel {
    List<Long> idsToRemove;

    public JuryDemoteBindingModel() {
        this.idsToRemove = new ArrayList<>();
    }

    public List<Long> getIdsToRemove() {
        return idsToRemove;
    }

    public JuryDemoteBindingModel setIdsToRemove(List<Long> idsToRemove) {
        this.idsToRemove = idsToRemove;
        return this;
    }
}
