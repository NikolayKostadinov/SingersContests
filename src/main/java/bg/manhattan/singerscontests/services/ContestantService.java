package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.service.ContestantServiceModel;
import bg.manhattan.singerscontests.model.view.ScenarioViewModel;

import java.security.Principal;

public interface ContestantService {

    ContestantServiceModel getContestantModel(Long editionId);

    void create(ContestantServiceModel contestant, Principal principal);

    boolean isRegistrar(Principal principal, Long id);

    ScenarioViewModel getContestantsForEditionOrderedByAgeGroupAndScenarioNumber(Long id);
}
