package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.events.PerformanceIssueEvent;
import bg.manhattan.singerscontests.events.UserChangeEmailEvent;
import bg.manhattan.singerscontests.events.UserRegisteredEvent;
import bg.manhattan.singerscontests.model.view.PerformanceIssueViewModel;
import bg.manhattan.singerscontests.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String REGISTRATION_EMAIL_TEMPLATE_NAME = "email/registration";
    private static final String UPDATE_EMAIL_TEMPLATE_NAME = "email/update-email";
    public static final String PERFORMANCE_ISSUE_EMAIL_TEMPLATE_NAME = "email/performance-issue";
    private static final boolean MULTIPART = true;
    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final String sender;
    private final String registrationSubject;

    private final String performanceIssueSubject;
    private final String adminEmail;


    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.sender}") String sender,
                            @Value("${mail.registration_mail_subject}") String registrationSubject,
                            @Value("${mail.performance_issue_mail_subject}")String performanceIssueSubject,
                            @Value("${mail.admin}") String adminEmail) {

        this.templateEngine = templateEngine;
        this.mailSender = javaMailSender;
        this.sender = sender;
        this.registrationSubject = registrationSubject;
        this.performanceIssueSubject = performanceIssueSubject;
        this.adminEmail = adminEmail;
    }

    @EventListener(UserRegisteredEvent.class)
    public void onUserRegistered(UserRegisteredEvent event) throws MessagingException {
        this.sendEmail(event.getEmail(), event.getFullName(), event.getLocale(), REGISTRATION_EMAIL_TEMPLATE_NAME);
    }

    @EventListener(UserChangeEmailEvent.class)
    public void onUserRegistered(UserChangeEmailEvent event) throws MessagingException {
        this.sendEmail(event.getEmail(), event.getFullName(), event.getLocale(), UPDATE_EMAIL_TEMPLATE_NAME);
    }

    @Override
    public void sendEmail(String email, String fullName, Locale locale, String templateName) throws MessagingException {

        // Prepare the evaluation context
        final Context context = new Context(locale);
        context.setVariable("fullName", fullName);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MULTIPART, "UTF-8");
        message.setSubject(this.registrationSubject);
        message.setFrom(this.sender);
        message.setTo(email);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process(templateName, context);
        message.setText(htmlContent, true /* isHtml */);

        // Send mail
        this.mailSender.send(mimeMessage);
    }

    @EventListener(PerformanceIssueEvent.class)
    public void onPerformanceIssue(PerformanceIssueEvent event) throws MessagingException {
        this.sendPerformanceIssueEmail(this.adminEmail, event.getSource(), event.getPerformanceProblem());
    }

    @Override
    public void sendPerformanceIssueEmail(String email, Object source, PerformanceIssueViewModel issue) throws MessagingException {
        // Prepare the evaluation context
        final Context context = new Context();
        context.setVariable("issue", issue);
        context.setVariable("source", source);

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, MULTIPART, "UTF-8");
        message.setSubject(this.performanceIssueSubject);
        message.setFrom(this.sender);
        message.setTo(email);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process(PERFORMANCE_ISSUE_EMAIL_TEMPLATE_NAME, context);
        message.setText(htmlContent, true );

        // Send mail
        this.mailSender.send(mimeMessage);
    }

}
