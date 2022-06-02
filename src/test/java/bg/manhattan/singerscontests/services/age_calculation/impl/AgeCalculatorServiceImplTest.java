package bg.manhattan.singerscontests.services.age_calculation.impl;

import bg.manhattan.singerscontests.exceptions.UnsupportedAgeCalculationType;
import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.services.age_calculation.AgeCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgeCalculatorServiceImplTest {
    private List<AgeCalculator> calculators;
    private AgeCalculationDto data;


    @BeforeEach
    void initialize() {
        AgeCalculator calculator1 = mock(AgeCalculator.class);
        AgeCalculator calculator2 = mock(AgeCalculator.class);
        AgeCalculator calculator3 = mock(AgeCalculator.class);

        data = new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_CONTEST);

        when(calculator2.canHandle(data)).thenReturn(true);
        this.calculators = List.of(calculator1, calculator2, calculator3);
    }


    @Test
    void calculateAge_with_known_AgeCalculationType_will_calculate_age() {
        //arrange
        AgeCalculatorServiceImpl calculatorService = new AgeCalculatorServiceImpl(this.calculators);

        //act
        calculatorService.calculateAge(data);

        //assert
        verify(calculators.get(1), times(1)).calculateAge(data);

    }

    @Test
    void calculateAge_with_unknown_AgeCalculationType_will_throw_exception() {
        //arrange
        AgeCalculatorServiceImpl calculatorService = new AgeCalculatorServiceImpl(this.calculators);
        AgeCalculationDto incorrectData = new AgeCalculationDto().setCalculationType(AgeCalculationType.YEAR_OF_BIRTH);

        //act&assert
        assertThrows(UnsupportedAgeCalculationType.class,
                ()-> calculatorService.calculateAge(incorrectData),
                "Age calculation type YEAR_OF_BIRTH not supported! " +
                        "Please configure the age calculation type in the system!");

    }
}
