package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;

public interface AgeCalculator {

    boolean canHandle(AgeCalculationDto data);

    int calculateAge(AgeCalculationDto data);
}
