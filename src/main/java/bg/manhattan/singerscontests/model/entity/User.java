package bg.manhattan.singerscontests.model.entity;

import bg.manhattan.singerscontests.model.ModelConstants;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static bg.manhattan.singerscontests.model.ModelConstants.USER_NAME_MAX_LENGTH;

@Entity
@Table(name="users")
public class User extends PersonBaseEntity{

    @Column(nullable = false, unique = true, length = USER_NAME_MAX_LENGTH)
    private String username;

    @Column(nullable = false, unique = false)
    private String email; // username of the user.

    @Column(nullable = false)
    private String password; // password of the user.


    @Column(name="phone_number", nullable = false)
    private String phoneNumber; // password of the user.

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<UserRole> roles;

    @ManyToMany(mappedBy = "managers",fetch = FetchType.EAGER)
    private Set<Contest> contests;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn
    private JuryMember juryMember;

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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public User setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public User setRoles(Set<UserRole> roles) {
        this.roles = roles;
        return this;
    }

    public Set<Contest> getContests() {
        return contests;
    }

    public User setContests(Set<Contest> contests) {
        this.contests = contests;
        return this;
    }

    public JuryMember getJuryMember() {
        return juryMember;
    }

    public User setJuryMember(JuryMember juryMember) {
        this.juryMember = juryMember;
        return this;
    }
}
