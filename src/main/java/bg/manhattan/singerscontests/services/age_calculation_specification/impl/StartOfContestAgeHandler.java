package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope("singleton")
public class StartOfContestAgeHandler extends BaseHandler {
    @Override
    protected boolean canHandle(AgeCalculationDto data) {
        return data != null
                && data.getCalculationType() != null
                && data.getCalculationType().equals(AgeCalculationType.START_OF_CONTEST);
    }

    @Override
    protected int handleStep(AgeCalculationDto data) {
        return data.getBirthDate().until(data.getEditionBeginDate()).getYears();
    }
}
