package bg.manhattan.singerscontests.services.age_calculation.impl;

import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StartOfContestAgeCalculatorTest {
    private StartOfContestAgeCalculator calculator;

    @BeforeEach
    void Initialize() {
        this.calculator = new StartOfContestAgeCalculator();
    }

    @Test
    void test_canHandle_calculationType_is_START_OF_YEAR_return_true() {
        assertTrue(this.calculator.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_CONTEST)));
    }

    @Test
    void test_canHandle_calculationType_is_not_START_OF_YEAR_return_false() {
        assertFalse(this.calculator.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_YEAR)));

        assertFalse(this.calculator.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.YEAR_OF_BIRTH)));
    }

    @Test
    void handle_calculate_age_correctly() {
        //arrange
        AgeCalculationDto dto12 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.START_OF_CONTEST)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2022, 3, 12));

        AgeCalculationDto dto13 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.START_OF_CONTEST)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2022, 4, 25));

        AgeCalculationDto dto14 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.START_OF_CONTEST)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2023, 5, 25));
        //act
        //assert
        assertEquals(12, this.calculator.calculateAge(dto12));
        assertEquals(13, this.calculator.calculateAge(dto13));
        assertEquals(14, this.calculator.calculateAge(dto14));
    }
}
