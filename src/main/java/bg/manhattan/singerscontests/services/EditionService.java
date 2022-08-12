package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import bg.manhattan.singerscontests.model.service.EditionDetailsServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

public interface EditionService {
    void create(EditionServiceModel editionModel);
    void edit(EditionServiceModel editionModel);

    EditionServiceModel getById(Long editionId);

    Edition getEntityById(Long editionId);

    void delete(Long id);

    List<LocalDate> getDatesForMonth(int month, int year);

    Page<EditionServiceModel> getEditionsAvailableForRegister(int pageNumber, int size);

    Page<EditionServiceModel> getEditionsByContestInFuture(Contest contest, int pageNumber, int size);

    EditionDetailsServiceModel getEditionDetails(Long editionId);

    void generateScenarioOrder(LocalDate targetDate);

    ContestServiceModelWithEditions getEditionsInFutureByContestId(Long contestId, int pageNumber, int size);

    boolean isEditionOwner(String userName, Long id);

    Page<EditionServiceModel> getEditionsClosedForSubscription(int pageNumber, int size);

    Page<EditionServiceModel> getEditionsActiveForJuryMember(Principal principal, int pageNumber, int size);

    Page<EditionServiceModel> getFinishedEditions(int pageNumber, int size);

    boolean isJuryDutyAvailable(String userName, Long id);
}
