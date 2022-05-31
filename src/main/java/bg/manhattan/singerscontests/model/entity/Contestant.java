package bg.manhattan.singerscontests.model.entity;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name="contestants")
public class Contestant extends PersonBaseEntity {

    @ManyToOne(optional = false)
    private Edition edition;

    @ManyToOne(optional = false)
    private AgeGroup ageGroup;

    @OneToMany(mappedBy="contestant")
    private List<Song> songs;

}
