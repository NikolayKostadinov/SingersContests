package bg.manhattan.singerscontests.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "user0", userDetailsServiceBeanName = "userDetailsService")
class ScenarioOrderControllerIntegrationTest {

    @Autowired
    public MockMvc mockMvc;

    @Test
    void getEditions() throws Exception {
        mockMvc.perform(get("/scenario"))
                .andExpect(status().isOk())
                .andExpect(view().name("scenario-order/editions"))
                .andExpect(model().attributeExists("editions"));
    }

    @Test
    void getScenarioOrder() throws Exception {
        mockMvc.perform(get("/scenario/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("scenario-order/order"))
                .andExpect(model().attributeExists("scenarioOrder"));
    }
}