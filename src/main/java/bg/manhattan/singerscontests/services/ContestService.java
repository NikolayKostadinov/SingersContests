package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.service.ContestServiceModel;

import java.util.List;

public interface ContestService {
    List<ContestServiceModel> getAllContests();

    void delete(Long id);
}
