package bg.manhattan.singerscontests.services;

import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService{

    void sendEmail(String email, String fullName, Locale locale, String templateName) throws MessagingException;
}
