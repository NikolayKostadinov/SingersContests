package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.service.ContestCreateServiceModel;
import bg.manhattan.singerscontests.model.service.ContestEditServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;

public interface ContestService {
    List<ContestServiceModel> getAllContests();

    Page<ContestServiceModel> getAllContestsByContestManager(Principal principal, boolean isAdmin, int pageNumber, int size);

    void delete(Long id);

    void create(ContestCreateServiceModel contestModel);

    ContestServiceModel getContestById(Long id);

    boolean isOwner(String userName, Long id);

    void update(ContestEditServiceModel map);

    ContestServiceModelWithEditions getContestByIdWithEditions(Long contestId);

    Contest getContestEntityById(Long contestId);
}
