package bg.manhattan.singerscontests.model.binding;

import java.util.ArrayList;
import java.util.List;

public class JuriDemodeBindingModel {
    List<Long> idsToRemove;

    public JuriDemodeBindingModel() {
        this.idsToRemove = new ArrayList<>();
    }

    public List<Long> getIdsToRemove() {
        return idsToRemove;
    }

    public JuriDemodeBindingModel setIdsToRemove(List<Long> idsToRemove) {
        this.idsToRemove = idsToRemove;
        return this;
    }
}
