package bg.manhattan.singerscontests.web.contestant;

import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.enums.EditionType;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.web.IntegrationTestWithInjectedUserDetails;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
class ContestantRegisterControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EditionRepository editionRepository;

    @Autowired
    private ContestRepository contestRepository;

    private Edition edition;

    @BeforeAll
    void setUpTests(){
        Contest contest = contestRepository.findAll().get(0);
        this.edition = new Edition()
                .setEditionType(EditionType.ATTENDING)
                .setNumber(1)
                .setRegulations("No Rules")
                .setContest(contest)
                .setAgeGroups(new HashSet<>( Arrays.asList(
                        new AgeGroup().setName("I").setMinAge(5).setMaxAge(8).setDisplayNumber(1),
                        new AgeGroup().setName("II").setMinAge(9).setMaxAge(12).setDisplayNumber(2),
                        new AgeGroup().setName("III").setMinAge(13).setMaxAge(16).setDisplayNumber(3),
                        new AgeGroup().setName("IV").setMinAge(17).setMaxAge(25).setDisplayNumber(4)
                )))
                .setBeginOfSubscriptionDate(LocalDate.of(2022,7,28))
                .setEndOfSubscriptionDate(LocalDate.of(2022,7,29))
                .setBeginDate(LocalDate.of(2022,7,30))
                .setEndDate(LocalDate.of(2022,7,31));
        edition.setId(1L);
        editionRepository.save(this.edition);
    }

    @AfterAll
    void tearDownTests(){
        editionRepository.delete(this.edition);
    }

    @Test
    void register() throws Exception {
        mockMvc.perform(get("/contestants/register/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("contestants/register"));
    }
}