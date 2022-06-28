package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;

public interface AgeCalculator {

    boolean canHandle(AgeCalculationDto data);

    int calculateAge(AgeCalculationDto data);
}
