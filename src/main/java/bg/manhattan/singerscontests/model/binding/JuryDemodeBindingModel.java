package bg.manhattan.singerscontests.model.binding;

import java.util.ArrayList;
import java.util.List;

public class JuryDemodeBindingModel {
    List<Long> idsToRemove;

    public JuryDemodeBindingModel() {
        this.idsToRemove = new ArrayList<>();
    }

    public List<Long> getIdsToRemove() {
        return idsToRemove;
    }

    public JuryDemodeBindingModel setIdsToRemove(List<Long> idsToRemove) {
        this.idsToRemove = idsToRemove;
        return this;
    }
}
