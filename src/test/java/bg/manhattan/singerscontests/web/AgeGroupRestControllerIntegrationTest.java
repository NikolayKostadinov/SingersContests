package bg.manhattan.singerscontests.web;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
class AgeGroupRestControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Test
    void getAgeGroup() {
    }
}
