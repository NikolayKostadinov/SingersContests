package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.AgeGroup;

import java.time.LocalDate;

public interface AgeGroupService {
    AgeGroup getAgeGroup(long editionId, LocalDate birthDate);
}
