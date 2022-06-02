package bg.manhattan.singerscontests.model.entity;

import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.enums.EditionType;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="editions", uniqueConstraints={@UniqueConstraint(columnNames={"contest_id", "number"})})
public class Edition extends BaseEntity {

    @Column(name = "edition_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private Integer number;

    @Column(name="begin_date", nullable = false)
    private LocalDate beginDate;

    @Column(name="end_date", nullable = false)
    private LocalDate endDate;

     @Column(name="begin_of_subscription", nullable = false)
    private LocalDate beginOfSubscriptionDate;

    @Column(name="end_of_subscription", nullable = false)
    private LocalDate endOfSubscriptionDate;

    @Column(name="age_calculation_type")
    @Enumerated(EnumType.STRING)
    private AgeCalculationType ageCalculationType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String regulations;

    @ManyToOne(optional = false)
    private Contest contest;

    @OneToMany(mappedBy = "edition")
    private List<PerformanceCategory> performanceCategories;

    @OneToMany(mappedBy = "edition")
    private List<AgeGroup> ageGroups;

    @OneToMany(mappedBy = "edition", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Contestant> contestants;

    public Edition() {
        this.contestants = new ArrayList<>();
    }
}
