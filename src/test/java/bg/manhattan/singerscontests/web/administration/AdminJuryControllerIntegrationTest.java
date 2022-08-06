package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.web.IntegrationTestWithInjectedUserDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="admin", userDetailsServiceBeanName="userDetailsService")
class AdminJuryControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void jury() throws Exception {
        mockMvc.perform(get("/administration/jury"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration/promote-jury"));
    }

    @Test
    void demodeJury() throws Exception {
        mockMvc.perform(get("/administration/jury/demode"))
                .andExpect(status().isOk())
                .andExpect(view().name("administration/demote-jury"));
    }

    @Test
    void edit() throws Exception {
        mockMvc.perform(get("/administration/jury/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("/administration/edit-jury-details"));
    }
}
