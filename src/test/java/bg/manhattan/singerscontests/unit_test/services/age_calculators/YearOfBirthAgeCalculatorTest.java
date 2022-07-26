package bg.manhattan.singerscontests.unit_test.services.age_calculators;

import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.services.age_calculators.YearOfBirthAgeCalculator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class YearOfBirthAgeCalculatorTest {
    private YearOfBirthAgeCalculator handler;

    @BeforeEach
    void setUp() {
        this.handler = new YearOfBirthAgeCalculator();
    }

    @Test
    void test_canHandle_calculationType_is_START_OF_YEAR_return_true() {
        assertTrue(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.YEAR_OF_BIRTH)));
    }

    @Test
    void test_canHandle_calculationType_is_not_START_OF_YEAR_return_false() {
        assertFalse(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_CONTEST)));

        assertFalse(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_YEAR)));
    }

    @Test
    void handle_calculate_age_correctly() {
        //arrange
        AgeCalculationDto dto12 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.YEAR_OF_BIRTH)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2021, 3, 12));

        AgeCalculationDto dto13 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.YEAR_OF_BIRTH)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2022, 4, 25));

        AgeCalculationDto dto14 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.YEAR_OF_BIRTH)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2023, 5, 25));
        //act
        //assert
        assertEquals(12, this.handler.calculateAge(dto12));
        assertEquals(13, this.handler.calculateAge(dto13));
        assertEquals(14, this.handler.calculateAge(dto14));
    }
}
