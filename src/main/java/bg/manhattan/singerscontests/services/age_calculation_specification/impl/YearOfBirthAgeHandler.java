package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class YearOfBirthAgeHandler extends BaseHandler {
    @Override
    protected boolean canHandle(AgeCalculationDto data) {
        return data != null
                && data.getCalculationType() != null
                && data.getCalculationType() == AgeCalculationType.YEAR_OF_BIRTH;
    }

    @Override
    protected int handleStep(AgeCalculationDto data) {
        return data.getEditionBeginDate().getYear() - data.getBirthDate().getYear();
    }
}
