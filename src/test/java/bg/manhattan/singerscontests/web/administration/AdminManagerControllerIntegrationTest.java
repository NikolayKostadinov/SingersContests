package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.web.IntegrationTestWithInjectedUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="admin", userDetailsServiceBeanName="userDetailsService")
class AdminManagerControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void managers() throws Exception {
        mockMvc.perform(get("/administration/managers"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration/managers-administration"));
    }

    @Test
    @WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
    void managers_fails_non_admin() throws Exception {
        mockMvc.perform(get("/administration/managers"))
                .andExpect(status().isForbidden());
    }

    @Test
    void edit() {
    }
}