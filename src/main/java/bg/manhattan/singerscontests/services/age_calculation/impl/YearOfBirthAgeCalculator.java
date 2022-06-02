package bg.manhattan.singerscontests.services.age_calculation.impl;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.services.age_calculation.AgeCalculator;
import org.springframework.stereotype.Component;

@Component
public class YearOfBirthAgeCalculator implements AgeCalculator {
    @Override
    public boolean canHandle(AgeCalculationDto data) {
        return data != null
                && data.getCalculationType() != null
                && data.getCalculationType() == AgeCalculationType.YEAR_OF_BIRTH;
    }

    @Override
    public int calculateAge(AgeCalculationDto data) {
        return data.getEditionBeginDate().getYear() - data.getBirthDate().getYear();
    }
}
