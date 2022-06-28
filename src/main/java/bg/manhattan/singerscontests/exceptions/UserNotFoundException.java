package bg.manhattan.singerscontests.exceptions;

public class UserNotFoundException extends Exception{

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
