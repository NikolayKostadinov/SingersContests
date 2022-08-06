package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.events.PerformanceIssueEvent;
import bg.manhattan.singerscontests.interseptor.ExecutionTimeInterceptor;
import bg.manhattan.singerscontests.model.view.PerformanceIssueViewModel;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
class AccountManageControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails{
    @Autowired
    private MockMvc mockMvc;

    @Value("${mail.port}")
    private int port;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String usename;

    @Value("${mail.password}")
    private String password;

    @MockBean
    private ExecutionTimeInterceptor executionTimeInterceptor;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    private GreenMail greenMail;

    @BeforeEach
    void setUp() throws Exception {
        doCallRealMethod().when(executionTimeInterceptor).preHandle(any(), any(), any());
        this.greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        this.greenMail.start();
        greenMail.setUser(usename, password);
    }

    @AfterEach
    void tearDown(){
        this.greenMail.stop();
    }


    @Test
    void profile() throws Exception {
        mockMvc.perform(get("/account/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/profile-change"));
    }

    @Test
    void email() throws Exception {
        mockMvc.perform(get("/account/email"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/email-change"));
    }

    @Test
    void testEmail() throws Exception {
        mockMvc.perform(post("/account/email").param("firstName", "Pesho")
                .param("email", "user0@test.com")
                .param("newEmail", "pesho@test.com")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("email"));

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        Assertions.assertEquals( 1, receivedMessages.length);
        MimeMessage receivedMessage = receivedMessages[0];

        String message = receivedMessage.getContent().toString();
        Assertions.assertTrue(message.contains("User User User 0"));

        String subject = receivedMessage.getSubject();
        Assertions.assertTrue(subject.contains("You have change you email in Singers Contest System!"));
    }

    @Test
    void password() throws Exception {
        mockMvc.perform(get("/account/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/password-change"));
    }

    @Test
    void testPassword() throws Exception {
        doAnswer(invocation -> {
            this.eventPublisher.publishEvent(
                    new PerformanceIssueEvent(this,
                            new PerformanceIssueViewModel("Too slow change password", 510L )));
            return null;
        }).when(executionTimeInterceptor).postHandle(any(), any(), any(), any());
        mockMvc.perform(post("/account/password").param("firstName", "Pesho")
                        .param("currentPassword", "P@ssw0rd")
                        .param("newPassword", "NewP@ssw0rd")
                        .param("confirmPassword", "NewP@ssw0rd")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/authentication/login"));

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        Assertions.assertEquals( 1, receivedMessages.length);
        MimeMessage receivedMessage = receivedMessages[0];

        String message = receivedMessage.getContent().toString();
        Assertions.assertTrue(message.contains("The Too slow change password was executed too slow!"));
        Assertions.assertTrue(message.contains("510ms"));

        String subject = receivedMessage.getSubject();
        Assertions.assertTrue(subject.contains("There was an performance issue!"));
    }

    @Test
    void deletePersonalData() throws Exception {
        mockMvc.perform(get("/account/delete_personal_data"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/account-delete"));
    }
}
