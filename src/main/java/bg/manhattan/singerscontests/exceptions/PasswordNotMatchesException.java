package bg.manhattan.singerscontests.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)

public class PasswordNotMatchesException extends RuntimeException {
}
