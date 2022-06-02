package bg.manhattan.singerscontests.services.age_calculation_specification;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;

public interface AgeCalculationHandler {

    int handle(AgeCalculationDto data);

    void setSuccessor(AgeCalculationHandler successor);
}
