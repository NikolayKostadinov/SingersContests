package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.service.EditionDetailsServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface EditionService {
    void insert(EditionServiceModel editionModel);

    EditionServiceModel getById(Long editionId);
    Edition getEntityById(Long editionId);

    void delete(Long id);

    List<LocalDate> getDatesForMonth(int month, int year);

    Page<EditionServiceModel> getFutureEditions(int pageNumber, int size);

    EditionDetailsServiceModel getEditionDetails(Long editionId);

    void generateScenarioOrder();
}
