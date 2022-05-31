package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;

@Entity
@Table(name="age_groups")
public class AgeGroup extends BaseEntity{

    @Column(length = 50, nullable = false)
    private String name;

    @Column(name="min_age", nullable = false)
    private int minAge;

    @Column(name="max_age", nullable = false)
    private int maxAge;

    @ManyToOne
    private Edition edition;



    public Edition getEdition() {
        return edition;
    }

    public void setEdition(Edition edition) {
        this.edition = edition;
    }
}
