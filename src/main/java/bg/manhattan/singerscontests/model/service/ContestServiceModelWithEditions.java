package bg.manhattan.singerscontests.model.service;

import bg.manhattan.singerscontests.model.entity.Edition;

import java.util.List;

public class ContestServiceModelWithEditions extends ContestEditServiceModel{
    private List<Edition> editions;

    public List<Edition> getEditions() {
        return editions;
    }

    public ContestServiceModelWithEditions setEditions(List<Edition> editions) {
        this.editions = editions;
        return this;
    }
}
