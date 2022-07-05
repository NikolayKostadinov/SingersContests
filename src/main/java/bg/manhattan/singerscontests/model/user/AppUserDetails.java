package bg.manhattan.singerscontests.model.user;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class AppUserDetails extends User {
    private final String fullName;


    public AppUserDetails(String username, String password, Collection<? extends GrantedAuthority> authorities, String fullName) {
        super(username, password, authorities);
        this.fullName = fullName;
    }

    public AppUserDetails(String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities, String fullName) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.fullName = fullName;
    }

    public String getFullName() {
        return fullName;
    }
}
