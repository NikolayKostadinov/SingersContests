package bg.manhattan.singerscontests.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException() {
        super(getMessage(null));
    }
    public UserNotFoundException(String user) {
        super(getMessage(user));
    }

    private static String getMessage(String user) {
        if (user == null || user.isBlank()) {
            return String.format("User %s not found", user);
        }
        return "User not found";
    }
}
