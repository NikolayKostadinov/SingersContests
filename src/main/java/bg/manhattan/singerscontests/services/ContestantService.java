package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.service.ContestantServiceModel;

import java.security.Principal;

public interface ContestantService {

    ContestantServiceModel getContestantModel(Long editionId);

    void create(ContestantServiceModel contestant, Principal principal);
}
