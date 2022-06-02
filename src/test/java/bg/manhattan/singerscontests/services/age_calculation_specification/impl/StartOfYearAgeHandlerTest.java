package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.exceptions.UnsupportedAgeCalculationType;
import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;

class StartOfYearAgeHandlerTest {

    private StartOfYearAgeHandler handler;

    @BeforeEach
    void Initialize() {
        this.handler = new StartOfYearAgeHandler();
    }

    @Test
    void test_canHandle_calculationType_is_START_OF_YEAR_return_true() {
        assertTrue(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_YEAR)));
    }

    @Test
    void test_canHandle_calculationType_is_not_START_OF_YEAR_return_false() {
        assertFalse(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_CONTEST)));

        assertFalse(this.handler.canHandle(
                new AgeCalculationDto().setCalculationType(AgeCalculationType.YEAR_OF_BIRTH)));
    }

    @Test
    void handle_calculate_age_correctly() {
        //arrange
        AgeCalculationDto dto12 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.START_OF_YEAR)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2022, 3, 12));

        AgeCalculationDto dto121 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.START_OF_YEAR)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2022, 4, 25));

        AgeCalculationDto dto13 = new AgeCalculationDto()
                .setCalculationType(AgeCalculationType.START_OF_YEAR)
                .setBirthDate(LocalDate.of(2009, 4, 25))
                .setEditionBeginDate(LocalDate.of(2023, 5, 25));
        //act
        //assert
        assertEquals(12, this.handler.handle(dto12));
        assertEquals(12, this.handler.handle(dto121));
        assertEquals(13, this.handler.handle(dto13));
    }

    @Test
    void handle_will_call_next_handler() {
        //arrange
        StartOfYearAgeHandler mockHandler = Mockito.mock(StartOfYearAgeHandler.class);
        AgeCalculationDto data = new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_CONTEST);
        Mockito.when(mockHandler.canHandle(data)).thenReturn(true);
        this.handler.setSuccessor(mockHandler);

        //act
        this.handler.handle(data);

        //assert
        Mockito.verify(mockHandler, times(1)).handle(data);
    }


    @Test
    void handle_will_throw_exception_if_no_handler_found() {
        //arrange
        StartOfYearAgeHandler mockHandler = Mockito.mock(StartOfYearAgeHandler.class);
        AgeCalculationDto data = new AgeCalculationDto().setCalculationType(AgeCalculationType.START_OF_CONTEST);

        //act
        assertThrowsExactly(UnsupportedAgeCalculationType.class, () -> this.handler.handle(data),
                () -> new UnsupportedAgeCalculationType(AgeCalculationType.START_OF_CONTEST.toString()).getMessage());

    }


}
