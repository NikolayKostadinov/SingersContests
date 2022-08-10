package bg.manhattan.singerscontests.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "user1", userDetailsServiceBeanName = "userDetailsService")
@Transactional
class RankingControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;



    @Test
    void getEditions() throws Exception {
        mockMvc.perform(get("/ranking/editions"))
                .andExpect(status().isOk())
                .andExpect(view().name("ranking/editions"))
                .andExpect(model().attributeExists("editions"));
    }

    @Test
    void getEditionRanking() throws Exception {
        mockMvc.perform(get("/ranking/editions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("ranking/result"))
                .andExpect(model().attributeExists("rankingOrder"));
    }
}