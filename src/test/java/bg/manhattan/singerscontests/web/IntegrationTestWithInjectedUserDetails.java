package bg.manhattan.singerscontests.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;

public abstract class IntegrationTestWithInjectedUserDetails {
    @Autowired
    UserDetailsService userDetailsService;
}
