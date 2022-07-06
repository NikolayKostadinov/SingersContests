package bg.manhattan.singerscontests.model.enums;

public enum AgeCalculationType {
    START_OF_YEAR("Аge at the beginning of the year"),
    START_OF_CONTEST("Age at the start of the competition"),
    YEAR_OF_BIRTH("Age by year of birth");

    private final String displayName;

    AgeCalculationType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
