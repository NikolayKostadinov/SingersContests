package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="users")
public class User extends PersonBaseEntity{

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email; // username of the user.

    private String password; // password of the user.

    private boolean isActive;

    private String imageUrl; // an url of user's picture.

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    public User() {
        this.roles = new HashSet<>();
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isActive() {
        return isActive;
    }

    public User setActive(boolean active) {
        isActive = active;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public User setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }
}
