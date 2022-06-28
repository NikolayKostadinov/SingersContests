package bg.manhattan.singerscontests.services.age_calculation.impl;

import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.services.AgeCalculator;
import org.springframework.stereotype.Component;

@Component
public class StartOfContestAgeCalculator implements AgeCalculator {
    @Override
    public boolean canHandle(AgeCalculationDto data) {
        return data != null
                && data.getCalculationType() != null
                && data.getCalculationType().equals(AgeCalculationType.START_OF_CONTEST);
    }

    @Override
    public int calculateAge(AgeCalculationDto data) {
        return data.getBirthDate().until(data.getEditionBeginDate()).getYears();
    }
}
