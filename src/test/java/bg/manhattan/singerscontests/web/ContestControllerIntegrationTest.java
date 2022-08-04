package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.repositories.ContestantRepository;
import bg.manhattan.singerscontests.repositories.UserRepository;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="manager1", userDetailsServiceBeanName="userDetailsService")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ContestControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ContestRepository contestRepository;

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
    void testCreate() throws Exception {
        mockMvc.perform(post("/contests/create")
                        .param("name", "Test Contest")
                        .param("managers", "1")
                        .param("managers", "2")
                        .with(csrf())
                )
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/contests"));
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


}
