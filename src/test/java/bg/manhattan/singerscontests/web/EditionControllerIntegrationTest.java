package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.repositories.EditionRepository;
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
@WithUserDetails(value = "manager1", userDetailsServiceBeanName = "userDetailsService")
class EditionControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private ContestRepository contestRepository;

    private Edition edition = (Edition) new Edition().setId(Long.MAX_VALUE);


    @Test
    void contests() throws Exception {
        mockMvc.perform(get("/editions"))
                .andExpect(status().isOk())
                .andExpect(view().name("editions/contests"));
    }

    @Test
    void contestsUnauthorized() throws Exception {
        mockMvc.perform(get("/editions").with(user("user1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void editions() throws Exception {
        mockMvc.perform(get("/editions/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("editions/editions"));
    }

    @Test
    void editionsUnauthorized() throws Exception {
        mockMvc.perform(get("/editions/" + this.edition.getId()).with(user("user1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void createEdition() throws Exception {
        mockMvc.perform(get("/editions/1/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("editions/edition-create"));
    }

    @Test
    void createEditionUnauthorized() throws Exception {
        mockMvc.perform(get("/editions/1/create").with(user("user1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void create() {
        // TODO: 28.7.2022 г. Implement test
    }

    @Test
    void edit() throws Exception {
        mockMvc.perform(get("/editions/edit/" + 1))
                .andExpect(status().isOk())
                .andExpect(view().name("editions/edition-edit"));
    }

    @Test
    void editUnauthorized() throws Exception {
        mockMvc.perform(get("/editions/editions/" + this.edition.getId()).with(user("user1")))
                .andExpect(status().isForbidden());
    }

    @Test
    void editEdition() {
        // TODO: 28.7.2022 г. Implement test
    }

    @Test
    void deleteEdition() {
        // TODO: 28.7.2022 г. Implement test
    }
}
