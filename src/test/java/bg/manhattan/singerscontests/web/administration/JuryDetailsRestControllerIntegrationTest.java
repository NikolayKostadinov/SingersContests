package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.entity.JuryMember;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.repositories.JuryMemberRepository;
import bg.manhattan.singerscontests.repositories.UserRepository;
import bg.manhattan.singerscontests.web.IntegrationTestWithInjectedUserDetails;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "admin", userDetailsServiceBeanName = "userDetailsService")
class JuryDetailsRestControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JuryMemberRepository juryMemberRepository;

    @Autowired
    private UserRepository userRepository;

    private JuryMember jm;

    @BeforeClass
    void setUp() {
        User user = userRepository.findById(1L).orElse(null);
        jm = new JuryMember()
                .setUser(user)
                .setDetails("Jury details")
                .setImageUrl("image url");
        this.juryMemberRepository.save(jm);
    }

    @AfterClass
    void tearDown() {
        this.juryMemberRepository.delete(jm);
    }

    @Test
    void getDetails() throws Exception {
        mockMvc.perform(get("/api/jury/100")
                        .accept("application/json"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.details", is("Jury details")))
                .andExpect(jsonPath("$.imageUrl", is("image url")));
    }
}