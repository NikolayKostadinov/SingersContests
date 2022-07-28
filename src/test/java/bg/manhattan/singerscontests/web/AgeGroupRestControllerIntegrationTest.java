package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.services.EditionService;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value = "user0", userDetailsServiceBeanName = "userDetailsService")
class AgeGroupRestControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails {

    @MockBean
    private EditionService editionService;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        when(editionService.getById(5L))
                .thenReturn(new EditionServiceModel()
                        .setAgeCalculationType(AgeCalculationType.START_OF_CONTEST)
                        .setBeginDate(LocalDate.of(2022, 9, 23))
                        .setAgeGroups(
                                new HashSet<>(
                                        Arrays.asList(
                                                new AgeGroupServiceModel().setId(1L).setName("I").setMinAge(5).setMaxAge(8),
                                                new AgeGroupServiceModel().setId(2L).setName("II").setMinAge(9).setMaxAge(12),
                                                new AgeGroupServiceModel().setId(3L).setName("III").setMinAge(13).setMaxAge(16),
                                                new AgeGroupServiceModel().setId(4L).setName("IV").setMinAge(17).setMaxAge(25)
                                        )
                                )
                        )
                );
        when(editionService.getById(6L)).thenThrow(new NotFoundException("Edition", 6L));
    }

    @Test
    void getAgeGroup() throws Exception {
        mockMvc.perform(get("/api/age-group/5/25-04-2009")
                        .accept("application/json"))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.name", is("III")));
    }

    @Test
    void getAgeGroupNotFound() throws Exception {
        mockMvc.perform(get("/api/age-group/6/25-04-2009")
                        .accept("application/json"))
                .andExpect(status().is(404))
                .andExpect(jsonPath("$.message", is("Resource not found")))
                .andExpect(jsonPath("$.details", containsString( "id: 6 not found!")));
    }
}
