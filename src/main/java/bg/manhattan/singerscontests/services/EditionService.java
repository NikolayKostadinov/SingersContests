package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;

public interface EditionService {
    void create(EditionServiceModel editionModel) throws NotFoundException;

    EditionServiceModel getById(Long editionId) throws NotFoundException;
}
