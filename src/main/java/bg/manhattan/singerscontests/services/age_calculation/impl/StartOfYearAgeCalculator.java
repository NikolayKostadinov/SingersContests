package bg.manhattan.singerscontests.services.age_calculation.impl;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.services.age_calculation.AgeCalculator;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class StartOfYearAgeCalculator implements AgeCalculator {
    @Override
    public boolean canHandle(AgeCalculationDto data) {
        return data != null
                && data.getCalculationType() != null
                && data.getCalculationType().equals(AgeCalculationType.START_OF_YEAR);
    }

    @Override
    public int calculateAge(AgeCalculationDto data) {
        LocalDate dateForCalculation = LocalDate.of(data.getEditionBeginDate().getYear(), 1, 1);
        return data.getBirthDate().until(dateForCalculation).getYears();
    }

}