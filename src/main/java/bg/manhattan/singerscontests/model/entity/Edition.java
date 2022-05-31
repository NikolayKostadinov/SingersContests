package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="editions")
public class Edition extends BaseEntity {

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

    /**
     * If this flag is "true" the age of the contestant will be calculated based on the beginning date of
     * the contest
     *
     * If this flag is "true" the age of the contestant will be calculated based on the beginning of the year of contest
     */
    @Column(name="age_calculated_from_begin")
    private Boolean isAgeCalculatedFromBeginData;

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
