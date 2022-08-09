package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.model.service.ContestantServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;

import java.util.List;
import java.util.Map;

public record RankingViewModel(EditionServiceModel edition,
                               Map<Long, String> juryMembers,
                               Map<AgeGroupServiceModel, List<ContestantServiceModel>> ageGroups) {
}
