package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;

public interface EditionService {
    void insert(EditionServiceModel editionModel) throws NotFoundException;

    EditionServiceModel getById(Long editionId) throws NotFoundException;

    void delete(Long id);
}
