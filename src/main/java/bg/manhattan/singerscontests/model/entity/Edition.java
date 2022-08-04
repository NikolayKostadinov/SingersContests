package bg.manhattan.singerscontests.model.entity;

import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.enums.EditionType;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "editions", uniqueConstraints = {@UniqueConstraint(columnNames = {"contest_id", "number"})})
public class Edition extends BaseEntity {

    @Column(name = "edition_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private EditionType editionType;

    @Column(nullable = false)
    private Integer number;

    @Column(name = "begin_date", nullable = false)
    private LocalDate beginDate;

    @Column(name = "end_date", nullable = false)
    private LocalDate endDate;

    @Column(name = "begin_of_subscription", nullable = false)
    private LocalDate beginOfSubscriptionDate;

    @Column(name = "end_of_subscription", nullable = false)
    private LocalDate endOfSubscriptionDate;

    @Column(name = "age_calculation_type")
    @Enumerated(EnumType.STRING)
    private AgeCalculationType ageCalculationType;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String regulations;

    @ManyToOne(optional = false)
    private Contest contest;

    @OneToMany(mappedBy = "edition", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<PerformanceCategory> performanceCategories;

    @OneToMany(mappedBy = "edition", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AgeGroup> ageGroups;

    @OneToMany(mappedBy = "edition", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Contestant> contestants;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<JuryMember> juryMembers;

    public Edition() {
        this.ageGroups = new HashSet<>();
        this.performanceCategories = new HashSet<>();
        this.juryMembers = new HashSet<>();
        this.contestants = new HashSet<>();
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

    public Set<PerformanceCategory> getPerformanceCategories() {
        return performanceCategories;
    }

    public Edition setPerformanceCategories(Set<PerformanceCategory> performanceCategories) {
        this.performanceCategories = performanceCategories;
        return this;
    }

    public Set<AgeGroup> getAgeGroups() {
        return ageGroups;
    }

    public Edition setAgeGroups(Set<AgeGroup> ageGroups) {
        this.ageGroups = ageGroups;
        return this;
    }

    public Set<Contestant> getContestants() {
        return contestants;
    }

    public Edition setContestants(Set<Contestant> contestants) {
        this.contestants = contestants;
        return this;
    }

    public Set<JuryMember> getJuryMembers() {
        return juryMembers;
    }

    public Edition setJuryMembers(Set<JuryMember> juryMembers) {
        this.juryMembers = juryMembers;
        return this;
    }
}
