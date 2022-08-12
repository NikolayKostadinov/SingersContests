package bg.manhattan.singerscontests.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "user0", userDetailsServiceBeanName = "userDetailsService")
class AgeGroupRestControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {


    @Autowired
    private MockMvc mockMvc;

    @Test
    void getAgeGroup() throws Exception {
        mockMvc.perform(get("/api/age-group/1/2009-04-25")
                        .accept("application/json"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name", is("III")));
    }

    @Test
    void getAgeGroupNotFound() throws Exception {
        mockMvc.perform(get("/api/age-group/"+ Long.MAX_VALUE+"/2009-04-25")
                        .accept("application/json"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message", is("Resource not found")))
                .andExpect(jsonPath("$.details", containsString( "id: "+Long.MAX_VALUE+" not found!")));
    }
}
