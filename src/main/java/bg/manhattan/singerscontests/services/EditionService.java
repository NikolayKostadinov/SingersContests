package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.service.EditionServiceModel;

public interface EditionService {
    void insert(EditionServiceModel editionModel);

    EditionServiceModel getById(Long editionId);

    void delete(Long id);
}
