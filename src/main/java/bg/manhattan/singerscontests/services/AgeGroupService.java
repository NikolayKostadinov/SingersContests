package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;

import java.time.LocalDate;

public interface AgeGroupService {
    AgeGroupServiceModel getAgeGroup(long editionId, LocalDate birthDate);
    AgeGroup getAgeGroupEntity(Edition edition, LocalDate birthDate);

    boolean isBirthDateInRange(Long contestId, LocalDate birthDate);
}
