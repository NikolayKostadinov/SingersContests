package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="jury_members")
public class JuryMember extends BaseEntity{
    @OneToOne
    private User user;

    @Column(nullable = false)
    @Lob
    private String details;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Edition> editions;

    public JuryMember() {
        this.editions = new HashSet<>();
    }

    public User getUser() {
        return user;
    }

    public JuryMember setUser(User user) {
        this.user = user;
        return this;
    }

    public String getDetails() {
        return details;
    }

    public JuryMember setDetails(String details) {
        this.details = details;
        return this;
    }

    public Set<Edition> getEditions() {
        return editions;
    }

    public JuryMember setEditions(Set<Edition> editions) {
        this.editions = editions;
        return this;
    }
}
