package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.UnsupportedAgeCalculationType;
import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.services.AgeCalculator;
import bg.manhattan.singerscontests.services.impl.AgeCalculatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AgeCalculatorServiceImplTest {
    @Mock
    AgeCalculator calculator1;
    @Mock
    AgeCalculator calculator2;
    @Mock
    AgeCalculator calculator3;

    @Test
    void calculateAge_with_known_AgeCalculationType_will_calculate_age() {
        //arrange
        AgeCalculationDto data = new AgeCalculationDto().setCalculationType(AgeCalculationType.YEAR_OF_BIRTH);
        when(calculator2.canHandle(data)).thenReturn(true);
        AgeCalculatorServiceImpl calculatorService = new AgeCalculatorServiceImpl(List.of(calculator1, calculator2, calculator3));

        //act
        calculatorService.calculateAge(data);

        //assert
        verify(calculator2, times(1)).calculateAge(data);

    }

    @Test
    void calculateAge_with_unknown_AgeCalculationType_will_throw_exception() {
        //arrange
        AgeCalculatorServiceImpl calculatorService = new AgeCalculatorServiceImpl(List.of(calculator1, calculator2, calculator3));
        AgeCalculationDto incorrectData = new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_YEAR);

        //act&assert
        assertThrows(UnsupportedAgeCalculationType.class,
                () -> calculatorService.calculateAge(incorrectData),
                "Age calculation type YEAR_OF_BIRTH not supported! " +
                        "Please configure the age calculation type in the system!");

    }
}
