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

    @OneToMany(mappedBy = "edition", fetch = FetchType.EAGER)
    private List<AgeGroup> ageGroups;

    @OneToMany(mappedBy = "edition")
    private List<Contestant> contestants;

    public Edition() {
        this.contestants = new ArrayList<>();
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public Edition setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public Edition setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public Edition setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Edition setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public LocalDate getBeginOfSubscriptionDate() {
        return beginOfSubscriptionDate;
    }

    public Edition setBeginOfSubscriptionDate(LocalDate beginOfSubscriptionDate) {
        this.beginOfSubscriptionDate = beginOfSubscriptionDate;
        return this;
    }

    public LocalDate getEndOfSubscriptionDate() {
        return endOfSubscriptionDate;
    }

    public Edition setEndOfSubscriptionDate(LocalDate endOfSubscriptionDate) {
        this.endOfSubscriptionDate = endOfSubscriptionDate;
        return this;
    }

    public AgeCalculationType getAgeCalculationType() {
        return ageCalculationType;
    }

    public Edition setAgeCalculationType(AgeCalculationType ageCalculationType) {
        this.ageCalculationType = ageCalculationType;
        return this;
    }

    public String getRegulations() {
        return regulations;
    }

    public Edition setRegulations(String regulations) {
        this.regulations = regulations;
        return this;
    }

    public Contest getContest() {
        return contest;
    }

    public Edition setContest(Contest contest) {
        this.contest = contest;
        return this;
    }

    public List<PerformanceCategory> getPerformanceCategories() {
        return performanceCategories;
    }

    public List<AgeGroup> getAgeGroups() {
        return ageGroups;
    }


    public List<Contestant> getContestants() {
        return contestants;
    }

}
