package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.repositories.UserRepository;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "manager1", userDetailsServiceBeanName = "userDetailsService")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EditionControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ContestRepository contestRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EditionRepository editionRepository;

    private Edition edition = (Edition) new Edition().setId(Long.MAX_VALUE);

    @BeforeAll
    void SetUp(){
        User manager1 = this.userRepository.findByUsername("manager1").orElse(null);
        Contest contest = this.contestRepository.findById(1L).orElse(null);
        contest.addManagers(List.of(manager1));
        this.contestRepository.save(contest);
    }

    @AfterAll
    void tearDoWn(){
        User manager1 = this.userRepository.findByUsername("manager1").orElse(null);
        Contest contest = this.contestRepository.findById(1L).orElse(null);
        contest.getManagers().remove(manager1);
        this.contestRepository.save(contest);
    }


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
