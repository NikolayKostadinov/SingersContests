package bg.manhattan.singerscontests.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String entityName, Object id) {
        super(String.format("%s id: %s not found!", entityName, id.toString()));
    }
}
