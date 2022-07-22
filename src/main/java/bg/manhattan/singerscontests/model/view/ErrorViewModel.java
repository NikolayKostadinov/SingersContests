package bg.manhattan.singerscontests.model.view;

public class ErrorViewModel {
    private final String message;
    private final String details;

    public ErrorViewModel(String message, String details) {
        this.message = message;
        this.details = details;
    }

    public String getMessage() {
        return message;
    }

    public String getDetails() {
        return details;
    }
}
