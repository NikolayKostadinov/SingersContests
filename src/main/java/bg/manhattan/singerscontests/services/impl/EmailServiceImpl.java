package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.services.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

@Service
public class EmailServiceImpl implements EmailService {
    private static final String EMAIL_TEMPLATE_NAME = "email/registration";
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

    @Override
    public void sendRegistrationEmail(String email, String fullName, Locale locale) throws MessagingException {

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
        final String htmlContent = this.templateEngine.process(EMAIL_TEMPLATE_NAME, context);
        message.setText(htmlContent, true /* isHtml */);

        // Send mail
        this.mailSender.send(mimeMessage);
    }

    private String generateMessageContent(String fullName, String imageResourceName) {
        Context context = new Context();
        context.setVariable("fullName", fullName);
        context.setVariable("imageResourceName", imageResourceName);
        return templateEngine.process(EMAIL_TEMPLATE_NAME, context);
    }

    public void sendMailWithInline(
            final String recipientName, final String recipientEmail, final String imageResourceName,
            final byte[] imageBytes, final String imageContentType, final Locale locale)
            throws MessagingException {

        // Prepare the evaluation context
        final Context ctx = new Context(locale);
        ctx.setVariable("name", recipientName);
        ctx.setVariable("subscriptionDate", new Date());
        ctx.setVariable("hobbies", Arrays.asList("Cinema", "Sports", "Music"));
        ctx.setVariable("imageResourceName", imageResourceName); // so that we can reference it from HTML

        // Prepare message using a Spring helper
        final MimeMessage mimeMessage = this.mailSender.createMimeMessage();
        final MimeMessageHelper message
                = new MimeMessageHelper(mimeMessage, true /* multipart */, "UTF-8");
        message.setSubject("Example HTML email with inline image");
        message.setFrom("thymeleaf@example.com");
        message.setTo(recipientEmail);

        // Create the HTML body using Thymeleaf
        final String htmlContent = this.templateEngine.process(EMAIL_TEMPLATE_NAME, ctx);
        message.setText(htmlContent, true /* isHtml */);

        // Add the inline image, referenced from the HTML code as "cid:${imageResourceName}"
        final InputStreamSource imageSource = new ByteArrayResource(imageBytes);
        message.addInline(imageResourceName, imageSource, imageContentType);

        // Send mail
        this.mailSender.send(mimeMessage);
    }
}
