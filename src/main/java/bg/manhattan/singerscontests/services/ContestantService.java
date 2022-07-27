package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.service.ContestantServiceModel;

public interface ContestantService {

    ContestantServiceModel getContestantModel(Long editionId);
}
