package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.service.ContestCreateServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;

import java.util.List;

public interface ContestService {
    List<ContestServiceModel> getAllContests();

    void delete(Long id);

    void create(ContestCreateServiceModel contestModel) throws UserNotFoundException;

    ContestServiceModel getContestById(Long id) throws NotFoundException;
}
