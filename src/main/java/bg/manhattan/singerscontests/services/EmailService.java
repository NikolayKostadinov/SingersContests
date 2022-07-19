package bg.manhattan.singerscontests.services;

import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService{
    void sendRegistrationEmail(String email, String fullName, Locale locale) throws MessagingException;
}
