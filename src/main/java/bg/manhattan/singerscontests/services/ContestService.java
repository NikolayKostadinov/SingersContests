package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.service.ContestCreateServiceModel;
import bg.manhattan.singerscontests.model.service.ContestEditServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;

import java.security.Principal;
import java.util.List;

public interface ContestService {
    List<ContestServiceModel> getAllContests();

    List<ContestServiceModel> getAllContestsByContestManager(Principal principal, boolean isAdmin) throws UserNotFoundException;

    void delete(Long id);

    void create(ContestCreateServiceModel contestModel) throws UserNotFoundException;

    ContestServiceModel getContestById(Long id) throws NotFoundException;

    void update(ContestEditServiceModel map) throws NotFoundException, UserNotFoundException;

    ContestServiceModelWithEditions getContestByIdWithEditions(Long contestId) throws NotFoundException;

    Contest getContestEntityById(Long contestId) throws NotFoundException;
}
