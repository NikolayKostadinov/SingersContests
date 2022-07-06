package bg.manhattan.singerscontests.model.enums;

public enum EditionType {
    ATTENDING("Live attending"),
    ONLINE("Online");

    private final String displayName;

    EditionType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
