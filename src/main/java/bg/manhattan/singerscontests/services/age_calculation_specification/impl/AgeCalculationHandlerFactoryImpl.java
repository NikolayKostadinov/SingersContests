package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.services.age_calculation_specification.AgeCalculationHandler;
import bg.manhattan.singerscontests.services.age_calculation_specification.AgeCalculationHandlerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Scope("singleton")
public class AgeCalculationHandlerFactoryImpl implements AgeCalculationHandlerFactory {
    private AgeCalculationHandler handler;

    public AgeCalculationHandlerFactoryImpl(List<AgeCalculationHandler> handlers) {
        if (!handlers.isEmpty()) {
            for (int i = 1; i < handlers.size(); i++) {
                handlers.get(i - 1).setSuccessor(handlers.get(i));
            }

            this.handler = handlers.get(0);
        }
    }

    @Override
    public AgeCalculationHandler getHandler() {
        return handler;
    }
}
