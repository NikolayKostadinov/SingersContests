package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.Contestant;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.entity.Song;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.test_utility.FakeDateTimeProvider;
import bg.manhattan.singerscontests.util.DateTimeProvider;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "jury", userDetailsServiceBeanName = "userDetailsService")
@Transactional
class JuryControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EditionRepository editionRepository;

    @Test
    void getEditionsForJuryMember() throws Exception {
        mockMvc.perform(get("/jury/editions"))
                .andExpect(status().isOk())
                .andExpect(view().name("jury/editions"))
                .andExpect(model().attributeExists("editions"));
    }

    @Test
    void getScenarioOrder() throws Exception {
        Edition edition = this.editionRepository.findAll()
                .stream()
                .filter(e -> e.getJuryMembers()
                        .stream()
                        .anyMatch(j -> j.getUser().getUsername().equals("jury")))
                .findFirst()
                .orElse(new Edition());

        DateTimeProvider.setCurrent(new FakeDateTimeProvider(edition.getBeginDate()));
        mockMvc.perform(get("/jury/scenario/" + edition.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("jury/order"))
                .andExpect(model().attributeExists("scenarioOrder"));
        DateTimeProvider.ResetToDefault();
    }

    @Test
    void getScenarioOrder_when_not_jury_will_return_Forbiden() throws Exception {
        Edition edition = this.editionRepository.findAll()
                .stream()
                .filter(e -> e.getJuryMembers()
                        .stream()
                        .noneMatch(j -> j.getUser().getUsername().equals("jury")))
                .findFirst()
                .orElse(new Edition());
        DateTimeProvider.setCurrent(new FakeDateTimeProvider(edition.getBeginDate()));
        mockMvc.perform(get("/jury/scenario/" + edition.getId()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error/403"));
        DateTimeProvider.ResetToDefault();
    }

    @Test
    void getScoreCard() throws Exception {
        Edition edition = this.editionRepository.findAll()
                .stream()
                .filter(e -> e.getJuryMembers()
                        .stream()
                        .anyMatch(j -> j.getUser().getUsername().equals("jury")))
                .findFirst()
                .orElse(new Edition());
        DateTimeProvider.setCurrent(new FakeDateTimeProvider(edition.getBeginDate()));
        mockMvc.perform(get("/jury/scorecard/" + edition.getId() +
                        "/" + edition.getContestants().stream().findFirst().orElse(new Contestant()).getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("jury/scorecard"))
                .andExpect(model().attributeExists("scoreCardHeader"))
                .andExpect(model().attributeExists("scoreModel"));
        DateTimeProvider.ResetToDefault();
    }

    @Test
    void getScoreCard_when_not_jury_will_return_Forbiden() throws Exception {
        Edition edition = this.editionRepository.findAll()
                .stream()
                .filter(e -> e.getJuryMembers()
                        .stream()
                        .noneMatch(j -> j.getUser().getUsername().equals("jury")))
                .findFirst()
                .orElse(new Edition());
        DateTimeProvider.setCurrent(new FakeDateTimeProvider(edition.getBeginDate()));
        mockMvc.perform(get("/jury/scorecard/" + edition.getId() +
                        "/" + edition.getContestants().stream().findFirst().orElse(new Contestant()).getId()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error/403"));
        DateTimeProvider.ResetToDefault();
    }

    @Test
    void insertScore() throws Exception {
        Edition edition = this.editionRepository.findAll()
                .stream()
                .filter(e -> e.getJuryMembers()
                        .stream()
                        .anyMatch(j -> j.getUser().getUsername().equals("jury")))
                .findFirst()
                .orElse(new Edition());
        DateTimeProvider.setCurrent(new FakeDateTimeProvider(edition.getBeginDate()));
        Contestant contestant = edition.getContestants().stream().findFirst().orElse(new Contestant());
        Song song = contestant.getSongs().stream().findFirst().get();
        mockMvc.perform(post("/jury/scorecard/" + edition.getId() + "/" + song.getId())
                        .param("id", song.getId().toString())
                        .param("editionId", edition.getId().toString())
                        .param("songId", song.getId().toString())
                        .param("artistry", "9")
                        .param("intonation", "9")
                        .param("repertoire", "9")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/jury/scenario/" + edition.getId()));
        DateTimeProvider.ResetToDefault();
    }

    @Test
    void insertScore_Error_input_redirect_to_same_page() throws Exception {
        Edition edition = this.editionRepository.findAll()
                .stream()
                .filter(e -> e.getJuryMembers()
                        .stream()
                        .anyMatch(j -> j.getUser().getUsername().equals("jury")))
                .findFirst()
                .orElse(new Edition());
        DateTimeProvider.setCurrent(new FakeDateTimeProvider(edition.getBeginDate()));
        Contestant contestant = edition.getContestants().stream().findFirst().orElse(new Contestant());
        Song song = contestant.getSongs().stream().findFirst().get();
        String url = "/jury/scorecard/" + edition.getId() + "/" + song.getId();
        mockMvc.perform(post(url)
                        .param("id", song.getId().toString())
                        .param("editionId", edition.getId().toString())
                        .param("songId", song.getId().toString())
                        .param("artistry", "19")
                        .param("intonation", "9")
                        .param("repertoire", "9")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:" + url));
        DateTimeProvider.ResetToDefault();
    }

    @Test
    void insertScore_when_not_jury_will_return_Forbiden() throws Exception {
        Edition edition = this.editionRepository.findAll()
                .stream()
                .filter(e -> e.getJuryMembers()
                        .stream()
                        .noneMatch(j -> j.getUser().getUsername().equals("jury")))
                .findFirst()
                .orElse(new Edition());
        DateTimeProvider.setCurrent(new FakeDateTimeProvider(edition.getBeginDate()));
        Contestant contestant = edition.getContestants().stream().findFirst().orElse(new Contestant());
        Song song = contestant.getSongs().stream().findFirst().get();
        mockMvc.perform(post("/jury/scorecard/" + edition.getId() + "/" + contestant.getId())
                        .param("id", song.getId().toString())
                        .param("editionId", edition.getId().toString())
                        .param("songId", song.getId().toString())
                        .param("artistry", "9")
                        .param("intonation", "9")
                        .param("repertoire", "9")
                        .with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(view().name("error/403"));
        DateTimeProvider.ResetToDefault();
    }
}