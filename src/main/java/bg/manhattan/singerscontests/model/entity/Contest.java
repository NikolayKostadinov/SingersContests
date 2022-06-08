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

    @ManyToMany(mappedBy = "contests", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Manager> managers;

    @OneToMany(mappedBy = "contest", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
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
}
