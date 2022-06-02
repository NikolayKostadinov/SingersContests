package bg.manhattan.singerscontests.exceptions;

public class UnsupportedAgeCalculationType extends IllegalArgumentException {
    public UnsupportedAgeCalculationType(String ageCalculationTypeName) {
        super(String.format("Age calculation type %s not supported!", ageCalculationTypeName));
    }
}
