package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.web.IntegrationTestWithInjectedUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
class AdminHomeControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithUserDetails(value="admin", userDetailsServiceBeanName="userDetailsService")
    void home() throws Exception {
        mockMvc.perform(get("/administration/home"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration/home"));
    }

    @Test
    @WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
    void home_fails_non_admin() throws Exception {
        mockMvc.perform(get("/administration/home"))
                .andExpect(status().isForbidden());
    }
}
