package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.interseptor.ExecutionTimeInterceptor;
import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.doCallRealMethod;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerIntegrationTest {

    @Value("${mail.port}")
    private int port;

    @Value("${mail.host}")
    private String host;

    @Value("${mail.username}")
    private String usename;

    @Value("${mail.password}")
    private String password;
    private GreenMail greenMail;
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExecutionTimeInterceptor executionTimeInterceptor;

    @BeforeEach
    void setUp() throws Exception {
        doCallRealMethod().when(executionTimeInterceptor).preHandle(Mockito.any(), Mockito.any(), Mockito.any());
        this.greenMail = new GreenMail(new ServerSetup(port, host, "smtp"));
        this.greenMail.start();
        greenMail.setUser(usename, password);
    }

    @AfterEach
    void tearDown(){
        this.greenMail.stop();
    }

    @Test
    void testRegistrationPageShow() throws Exception {
        mockMvc.perform(get("/authentication/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("authentication/register"));
    }

    @Test
    void testUserRegistration() throws Exception {
        mockMvc.perform(post("/authentication/register")
                        .param("firstName", "Pesho")
                        .param("middleName", "Ivanov")
                        .param("lastName", "Peshev")
                        .param("username", "pesho")
                        .param("email", "pesho@test.com")
                        .param("phoneNumber", "+123456748")
                        .param("password", "P@ssw0rd")
                        .param("confirmPassword", "P@ssw0rd")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        MimeMessage[] receivedMessages = greenMail.getReceivedMessages();
        Assertions.assertEquals( 1, receivedMessages.length);
        MimeMessage receivedMessage = receivedMessages[0];

        String message = receivedMessage.getContent().toString();
        Assertions.assertTrue(message.contains("Pesho Ivanov Peshev"));

        String subject = receivedMessage.getSubject();
        Assertions.assertTrue(subject.contains("You have registered in Singers Contest System!"));

    }

    @Test
    void testLoginPageShow() throws Exception {
        mockMvc.perform(get("/authentication/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("authentication/login"));
    }

    @Test
    void testUserLogin() throws Exception {
        mockMvc.perform(post("/authentication/login")
                        .param("username", "admin")
                        .param("password", "P@ssw0rd")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }
}
