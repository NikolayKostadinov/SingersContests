package bg.manhattan.singerscontests.services.impl.age_calculators;

import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.services.AgeCalculator;
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
