package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;

@Entity
@Table(name="age_groups")
public class AgeGroup extends BaseEntity{

    @Column(length = 51, nullable = false)
    private String name;

    @Column(name = "display_number", nullable = false)
    public Integer displayNumber;

    @Column(name="min_age", nullable = false)
    private int minAge;

    @Column(name="max_age", nullable = false)
    private int maxAge;

    @ManyToOne
    private Edition edition;

    @Override
    public AgeGroup setId(Long id) {
        super.setId(id) ;
        return this;
    }
    public String getName() {
        return name;
    }

    public AgeGroup setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getDisplayNumber() {
        return displayNumber;
    }

    public AgeGroup setDisplayNumber(Integer displayNumber) {
        this.displayNumber = displayNumber;
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

    public AgeGroup setEdition(Edition edition) {
        this.edition = edition;
        return this;
    }
}
