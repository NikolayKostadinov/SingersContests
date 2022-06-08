package bg.manhattan.singerscontests.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="managers")
public class Manager extends BaseEntity {
    @OneToOne
    private User user;

    @ManyToMany
    private Set<Contest> contests;

    public Manager() {
        this.contests = new HashSet<>();
    }

    public User getUser() {
        return user;
    }

    public Manager setUser(User user) {
        this.user = user;
        return this;
    }

    public Set<Contest> getContests() {
        return contests;
    }

    public Manager setContests(Set<Contest> contests) {
        this.contests = contests;
        return this;
    }
}
