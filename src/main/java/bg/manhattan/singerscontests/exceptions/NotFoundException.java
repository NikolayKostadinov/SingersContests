package bg.manhattan.singerscontests.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String entityName, Long id) {
        super(String.format("%s id: %d not found!", entityName, id));
    }
}
