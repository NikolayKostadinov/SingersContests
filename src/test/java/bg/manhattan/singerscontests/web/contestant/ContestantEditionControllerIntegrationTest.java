package bg.manhattan.singerscontests.web.contestant;

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
@WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
class ContestantEditionControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void editions() throws Exception {
        mockMvc.perform(get("/contestants/editions"))
                .andExpect(status().isOk())
                .andExpect(view().name("contestants/editions"));
    }

    @Test
    void editionDetails() throws Exception {
        mockMvc.perform(get("/contestants/editions/details/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("contestants/edition-details"));
    }

    @Test
    void editionDetailsNotFound() throws Exception {
        mockMvc.perform(get("/contestants/editions/details/" + (Long.MAX_VALUE)))
                .andExpect(status().isNotFound())
                .andExpect(view().name("error/error"));
    }
}
