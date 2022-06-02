package bg.manhattan.singerscontests.model.entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="contests")
public class Contest extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "contest", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Edition> editions;

    public Contest() {
        this.editions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public Contest setName(String name) {
        this.name = name;
        return this;
    }

    public List<Edition> getEditions() {
        return editions;
    }
}
