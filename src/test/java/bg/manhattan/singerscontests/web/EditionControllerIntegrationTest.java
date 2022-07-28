package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.enums.EditionType;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.services.EditionService;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="manager1", userDetailsServiceBeanName="userDetailsService")
class EditionControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

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

        editionRepository.save(this.edition);
    }

    @AfterAll
    void tearDownTests(){
        editionRepository.delete(this.edition);
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
        mockMvc.perform(get("/editions/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editions/editions"));
    }

    @Test
    void editionsUnauthorized() throws Exception {
        mockMvc.perform(get("/editions/1").with(user("user1")))
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
        mockMvc.perform(get("/editions/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("editions/edition-edit"));
    }

    @Test
    void editUnauthorized() throws Exception {
        mockMvc.perform(get("/editions/editions/1").with(user("user1")))
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