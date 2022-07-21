package bg.manhattan.singerscontests.events;

import org.springframework.context.ApplicationEvent;

import java.util.Locale;

public class UserRegisteredEvent extends ApplicationEvent {
    private final String email;
    private final String fullName;
    private final Locale locale;

    public UserRegisteredEvent(Object source, String email, String fullName, Locale locale) {
        super(source);
        this.email = email;
        this.fullName = fullName;
        this.locale = locale;
    }

    public String getEmail() {
        return email;
    }

    public String getFullName() {
        return fullName;
    }

    public Locale getLocale() {
        return locale;
    }
}
