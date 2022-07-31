package bg.manhattan.singerscontests.exceptions;

import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)

public class UnsupportedAgeCalculationType extends IllegalStateException {
    public UnsupportedAgeCalculationType(String ageCalculationTypeName) {
        super(
                String.format("Age calculation type %s not supported! " +
                        "Please configure the age calculation type in the system!", ageCalculationTypeName));
    }

    public UnsupportedAgeCalculationType(AgeCalculationDto data) {
        this(data.getCalculationType().name());
    }
}
