package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.services.age_calculation_specification.AgeCalculationHandler;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

class AgeCalculationHandlerFactoryImplTest {

    @Test
    void get_Handler_will_return_Handler_if_there_is() {
        //arrange
        AgeCalculationDto data = new AgeCalculationDto();
        BaseHandler mockHandler1 = Mockito.mock(BaseHandler.class);
        Mockito.when(mockHandler1.canHandle(data)).thenReturn(false);
        BaseHandler mockHandler2 = Mockito.mock(BaseHandler.class);
        Mockito.when(mockHandler2.canHandle(data)).thenReturn(false);
        BaseHandler mockHandler3 = Mockito.mock(BaseHandler.class);
        Mockito.when(mockHandler3.canHandle(data)).thenReturn(true);

        List<AgeCalculationHandler> handlers = List.of(mockHandler1,mockHandler2,mockHandler3);
        AgeCalculationHandlerFactoryImpl factory = new AgeCalculationHandlerFactoryImpl(handlers);

        //act
        factory.getHandler();

        Mockito.verify(mockHandler1, Mockito.times(1)).setSuccessor(mockHandler2);
        Mockito.verify(mockHandler2, Mockito.times(1)).setSuccessor(mockHandler3);

    }
}
