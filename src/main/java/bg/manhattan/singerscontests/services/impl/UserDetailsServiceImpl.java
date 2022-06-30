package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.security.AppUserDetails;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.services.UserService;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserService userService;

    public UserDetailsServiceImpl(UserService repository) {
        this.userService = repository;
    }


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        UserServiceModel user = this.userService.getUserByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + userName + " not found!"));
        return mapToUserDetails(user);
    }

    private static UserDetails mapToUserDetails(UserServiceModel user){
        List<GrantedAuthority> authorities = user
                .getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toList());

        return new AppUserDetails(user.getUsername(),
                user.getPassword(),
                authorities,
                user.getFullName());
    }
}