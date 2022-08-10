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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="admin", userDetailsServiceBeanName="userDetailsService")
class AdminAdministratorsControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AdminAdministratorsController controller;

    @Test
    void admins() throws Exception {
        mockMvc.perform(get("/administration/administrators"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration/roles"))
                .andExpect(model().attribute("roleDisplayName","Administrators"));
    }

    @Test
    @WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
    void managers_fails_non_admin() throws Exception {
        mockMvc.perform(get("/administration/administrators"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getRoleDisplayName() throws Exception {
        // act
        String roleDisplayName = this.controller.getRoleDisplayName();
        // assert
        assertEquals("Administrators", roleDisplayName);
    }


}