package bg.manhattan.singerscontests.model.entity;


import bg.manhattan.singerscontests.model.enums.ContestType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="contests")
public class Contest extends BaseEntity{

    @Column(nullable = false)
    private String name;

    @Column(name = "contest_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private ContestType contestType;

    @OneToMany(mappedBy = "contest", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Edition> editions;

    public Contest() {
        this.editions = new ArrayList<>();
    }
}
