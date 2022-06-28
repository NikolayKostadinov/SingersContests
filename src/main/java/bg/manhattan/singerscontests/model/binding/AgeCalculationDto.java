package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public class AgeCalculationDto {

    @NonNull
    private AgeCalculationType calculationType;

    @NonNull
    private LocalDate editionBeginDate;

    @NonNull
    private LocalDate birthDate;

    public AgeCalculationType getCalculationType() {
        return calculationType;
    }

    public AgeCalculationDto setCalculationType(AgeCalculationType calculationType) {
        this.calculationType = calculationType;
        return this;
    }

    public LocalDate getEditionBeginDate() {
        return editionBeginDate;
    }

    public AgeCalculationDto setEditionBeginDate(LocalDate editionBeginDate) {
        this.editionBeginDate = editionBeginDate;
        return this;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public AgeCalculationDto setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
        return this;
    }
}
