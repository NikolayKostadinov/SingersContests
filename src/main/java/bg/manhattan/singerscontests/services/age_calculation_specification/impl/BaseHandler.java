package bg.manhattan.singerscontests.services.age_calculation_specification.impl;

import bg.manhattan.singerscontests.exceptions.UnsupportedAgeCalculationType;
import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.services.age_calculation_specification.AgeCalculationHandler;

public abstract class BaseHandler implements AgeCalculationHandler {

    //START_OF_YEAR, START_OF_CONTEST, YEAR_OF_BIRTH

        private AgeCalculationHandler successor;

        @Override
        public int handle(AgeCalculationDto data) {
            if (this.canHandle(data))
            {
                return this.handleStep(data);
            }

            if (this.successor != null)
            {
                return this.successor.handle(data);
            }

            throw new UnsupportedAgeCalculationType(data.getCalculationType().toString());
        }

        @Override
        public void setSuccessor(AgeCalculationHandler successor) {
            this.successor = successor;
        }

        protected abstract boolean canHandle(AgeCalculationDto data);

        protected abstract int handleStep(AgeCalculationDto data);

}
