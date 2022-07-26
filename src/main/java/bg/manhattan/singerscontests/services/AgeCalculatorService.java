package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;

public interface AgeCalculatorService {
    int calculateAge(AgeCalculationDto data);
}
