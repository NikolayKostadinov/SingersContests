package bg.manhattan.singerscontests.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AgeGroupNotFoundException extends RuntimeException {
    public AgeGroupNotFoundException(String message) {
        super(message);
    }
}
