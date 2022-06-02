package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Scope("singleton")
public class StartOfYearAgeHandler extends BaseHandler {
    @Override
    protected boolean canHandle(AgeCalculationDto data) {
        return data != null
                && data.getCalculationType() != null
                && data.getCalculationType().equals(AgeCalculationType.START_OF_YEAR);
    }

    @Override
    protected int handleStep(AgeCalculationDto data) {
        LocalDate dateForCalculation = LocalDate.of(data.getEditionBeginDate().getYear(), 1, 1);
        return data.getBirthDate().until(dateForCalculation).getYears();
    }
}
