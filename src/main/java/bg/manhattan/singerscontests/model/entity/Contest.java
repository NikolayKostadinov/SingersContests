package bg.manhattan.singerscontests.model.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="contests")
public class Contest extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> managers;

    @OneToMany(mappedBy = "contest", fetch = FetchType.EAGER)
    private Set<Edition> editions;

    public Contest() {
        this.managers = new HashSet<>();
        this.editions = new HashSet<>();
    }

    public String getName() {
        return name;
    }

    public Contest setName(String name) {
        this.name = name;
        return this;
    }

    public Set<Edition> getEditions() {
        return editions;
    }

    public Contest setEditions(Set<Edition> editions) {
        this.editions = editions;
        return this;
    }

    public Set<User> getManagers() {
        return managers;
    }

    public Contest setManagers(Set<User> managers) {
        this.managers = managers;
        return this;
    }

    public Contest addManagers(List<User> managers) {
        this.managers.addAll(managers);
        return this;
    }
}
