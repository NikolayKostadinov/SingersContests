package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.UnsupportedAgeCalculationType;
import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.services.AgeCalculator;
import bg.manhattan.singerscontests.services.age_calculation.AgeCalculatorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgeCalculatorServiceImpl implements AgeCalculatorService {
    private final List<AgeCalculator> calculators;

    public AgeCalculatorServiceImpl(List<AgeCalculator> calculators) {
        this.calculators = calculators;
    }


    @Override
    public int calculateAge(AgeCalculationDto data) {
        return this.calculators.
                stream().
                filter(c -> c.canHandle(data))
                .findFirst()
                .orElseThrow(() -> new UnsupportedAgeCalculationType(data))
                .calculateAge(data);
    }
}
