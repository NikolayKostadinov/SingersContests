package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.model.service.ContestantServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;

import java.util.List;
import java.util.Map;

public class ScenarioViewModel {
    private final EditionServiceModel edition;
    private final Map<AgeGroupServiceModel, List<ContestantServiceModel>> ageGroups;

    public ScenarioViewModel(EditionServiceModel edition, Map<AgeGroupServiceModel, List<ContestantServiceModel>> ageGroups) {
        this.edition = edition;
        this.ageGroups = ageGroups;
    }

    public EditionServiceModel getEdition() {
        return edition;
    }

    public Map<AgeGroupServiceModel, List<ContestantServiceModel>> getAgeGroups() {
        return ageGroups;
    }


}
