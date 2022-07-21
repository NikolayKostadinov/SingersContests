package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.events.UserChangeEmailEvent;
import bg.manhattan.singerscontests.events.UserRegisteredEvent;
import bg.manhattan.singerscontests.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String REGISTRATION_EMAIL_TEMPLATE_NAME = "email/registration";
    private static final String UPDATE_EMAIL_TEMPLATE_NAME = "email/update-email";
    private static final boolean MULTIPART = true;
    public static final String LOGO_PATH = "static/images/logo.png";
    private static final String IMAGE_RESOURCE_NAME = "logo.png";
    private static final String IMAGE_CONTENT_TYPE = "image/png";

    private final TemplateEngine templateEngine;
    private final JavaMailSender mailSender;
    private final String sender;
    private final String registrationSubject;

    public EmailServiceImpl(TemplateEngine templateEngine,
                            JavaMailSender javaMailSender,
                            @Value("${mail.sender}") String sender,
                            @Value("${mail.registration_mail_subject}") String registrationSubject) {
        this.templateEngine = templateEngine;
        this.mailSender = javaMailSender;
        this.sender = sender;
        this.registrationSubject = registrationSubject;
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
}
