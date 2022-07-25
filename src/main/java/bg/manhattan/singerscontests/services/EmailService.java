package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.view.PerformanceIssueViewModel;

import javax.mail.MessagingException;
import java.util.Locale;

public interface EmailService{

    void sendEmail(String email, String fullName, Locale locale, String templateName) throws MessagingException;
    void sendPerformanceIssueEmail(String email, Object source, PerformanceIssueViewModel issue) throws MessagingException;
}
