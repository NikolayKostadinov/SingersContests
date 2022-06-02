package bg.manhattan.singerscontests.services.age_calculation;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;

public interface AgeCalculatorService {
    int calculateAge(AgeCalculationDto data);
}
