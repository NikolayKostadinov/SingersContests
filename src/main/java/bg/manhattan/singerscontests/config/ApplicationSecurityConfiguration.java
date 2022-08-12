package bg.manhattan.singerscontests.config;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.services.impl.UserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class ApplicationSecurityConfiguration {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserService userService){
        return new UserDetailsServiceImpl(userService);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
            .authorizeRequests()
                // allow anonymous access to all static resources
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                // additional CSS library
                .antMatchers("/vendor/**").permitAll()
                .antMatchers("/multiselect/**").permitAll()
                // allows anonymous access to home, login and registration
                .antMatchers("/", "/authentication/login", "/authentication/login-error").permitAll()
                // fine gained permission for certain functionalities
                .antMatchers("/authentication/register").anonymous()
                .antMatchers("/administration/**").hasAnyRole(UserRoleEnum.ADMIN.name())
                .antMatchers("/contests/**", "/editions/**").hasAnyRole(UserRoleEnum.CONTEST_MANAGER.name(),
                        UserRoleEnum.ADMIN.name())
                .antMatchers("/jury/**").hasAnyRole(UserRoleEnum.JURY_MEMBER.name())
                .antMatchers("/actuator/**").hasAnyRole(UserRoleEnum.ADMIN.name())
                // forbid all other for anonymous users;
                .anyRequest().authenticated()
            .and()
                // configuration of form login
                .formLogin()
                // the custom login form
                .loginPage("/authentication/login")
                // the name of the username form field
                .usernameParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY)
                // the name of the password form field
                .passwordParameter(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_PASSWORD_KEY)
                // where to go in case that the login is successful
                .defaultSuccessUrl("/")
                // where to go in case that the login failed
                .failureForwardUrl("/authentication/login-error")
            .and()
                .logout()
                // the logout page
                    .logoutUrl("/authentication/logout")
                    .logoutSuccessUrl("/")
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID")
            .and()
                .build();
    }
}
