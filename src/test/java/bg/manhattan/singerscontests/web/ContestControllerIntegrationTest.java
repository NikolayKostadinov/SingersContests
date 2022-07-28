package bg.manhattan.singerscontests.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="manager1", userDetailsServiceBeanName="userDetailsService")
class ContestControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contests() throws Exception {
        mockMvc.perform(get("/contests"))
                .andExpect(status().isOk())
                .andExpect(view().name("contests/contests"));
    }

    @Test
    void contestsUnauthorized() throws Exception {
        mockMvc.perform(get("/contests").with(user("user1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void create() throws Exception {
        mockMvc.perform(get("/contests/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("contests/contest-create"));
    }

    @Test
    void createUnauthorized() throws Exception {
        mockMvc.perform(get("/contests/create").with(user("user1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testCreate() {
        // TODO: 28.7.2022 г. Implement test
    }

    @Test
    void edit() throws Exception {
        mockMvc.perform(get("/contests/edit/1"))
                .andExpect(status().isOk());
    }

    @Test
    void editUnauthorized() throws Exception {
        mockMvc.perform(get("/contests/edit/1").with(user("user1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void testEdit() {
        // TODO: 28.7.2022 г. Implement test
    }

    @Test
    void delete() {
        // TODO: 28.7.2022 г. Implement test
    }
}