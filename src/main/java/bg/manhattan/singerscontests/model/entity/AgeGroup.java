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

    public String getName() {
        return name;
    }

    public AgeGroup setName(String name) {
        this.name = name;
        return this;
    }

    public int getMinAge() {
        return minAge;
    }

    public AgeGroup setMinAge(int minAge) {
        this.minAge = minAge;
        return this;
    }

    public int getMaxAge() {
        return maxAge;
    }

    public AgeGroup setMaxAge(int maxAge) {
        this.maxAge = maxAge;
        return this;
    }

    public Edition getEdition() {
        return edition;
    }
}
