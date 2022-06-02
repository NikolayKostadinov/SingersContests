package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class StartOfContestAgeHandlerTest {
    private StartOfContestAgeHandler handler;

    @BeforeEach
    void Initialize() {
        this.handler = new StartOfContestAgeHandler();
    }

    @Test
    void test_canHandle_calculationType_is_START_OF_YEAR_return_true() {
        assertTrue(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_CONTEST)));
    }

    @Test
    void test_canHandle_calculationType_is_not_START_OF_YEAR_return_false() {
        assertFalse(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_YEAR)));

        assertFalse(this.handler.canHandle(
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
        assertEquals(12, this.handler.handle(dto12));
        assertEquals(13, this.handler.handle(dto13));
        assertEquals(14, this.handler.handle(dto14));
    }
}
