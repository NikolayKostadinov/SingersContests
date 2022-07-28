package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;

import java.time.LocalDate;

public interface AgeGroupService {
    AgeGroupServiceModel getAgeGroup(long editionId, LocalDate birthDate);
}
