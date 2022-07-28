package bg.manhattan.singerscontests.web;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails(value="user0", userDetailsServiceBeanName="userDetailsService")
class AccountManageControllerIntegrationTest extends IntegrationTestWithInjectedUserDetails{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void profile() throws Exception {
        mockMvc.perform(get("/account/profile"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/profile-change"));
    }

    @Test
    void testProfile() {
        // TODO: 28.7.2022 г. Implement test
    }

    @Test
    void email() throws Exception {
        mockMvc.perform(get("/account/email"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/email-change"));
    }

    @Test
    void testEmail() {
    }

    @Test
    void password() throws Exception {
        mockMvc.perform(get("/account/password"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/password-change"));
    }

    @Test
    void testPassword() {
    }

    @Test
    void deletePersonalData() throws Exception {
        mockMvc.perform(get("/account/delete_personal_data"))
                .andExpect(status().isOk())
                .andExpect(view().name("account-manage/account-delete"));
    }

    @Test
    void testDeletePersonalData() {
        // TODO: 28.7.2022 г. Implement test
    }
}
