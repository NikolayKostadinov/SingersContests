package bg.manhattan.singerscontests.model.service;

import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.enums.EditionType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class EditionDetailsServiceModel {

    private Long id;
    private Integer number;
    private EditionType editionType;
    private AgeCalculationType ageCalculationType;
    private LocalDate beginDate;
    private LocalDate endDate;
    private LocalDate beginOfSubscriptionDate;
    private LocalDate endOfSubscriptionDate;
    private String regulations;
    private Long contestId;
    private String contestName;
    private Set<PerformanceCategoryServiceModel> performanceCategories;
    private Set<AgeGroupServiceModel> ageGroups;
    private Set<JuryMemberServiceModel> juryMembers;

    public EditionDetailsServiceModel() {
        this.performanceCategories = new HashSet<>();
        this.ageGroups = new HashSet<>();
        this.juryMembers = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public EditionDetailsServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public EditionDetailsServiceModel setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public EditionDetailsServiceModel setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public AgeCalculationType getAgeCalculationType() {
        return ageCalculationType;
    }

    public EditionDetailsServiceModel setAgeCalculationType(AgeCalculationType ageCalculationType) {
        this.ageCalculationType = ageCalculationType;
        return this;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public EditionDetailsServiceModel setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EditionDetailsServiceModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public LocalDate getBeginOfSubscriptionDate() {
        return beginOfSubscriptionDate;
    }

    public EditionDetailsServiceModel setBeginOfSubscriptionDate(LocalDate beginOfSubscriptionDate) {
        this.beginOfSubscriptionDate = beginOfSubscriptionDate;
        return this;
    }

    public LocalDate getEndOfSubscriptionDate() {
        return endOfSubscriptionDate;
    }

    public EditionDetailsServiceModel setEndOfSubscriptionDate(LocalDate endOfSubscriptionDate) {
        this.endOfSubscriptionDate = endOfSubscriptionDate;
        return this;
    }

    public String getRegulations() {
        return regulations;
    }

    public EditionDetailsServiceModel setRegulations(String regulations) {
        this.regulations = regulations;
        return this;
    }

    public Long getContestId() {
        return contestId;
    }

    public EditionDetailsServiceModel setContestId(Long contestId) {
        this.contestId = contestId;
        return this;
    }

    public String getContestName() {
        return contestName;
    }

    public EditionDetailsServiceModel setContestName(String contestName) {
        this.contestName = contestName;
        return this;
    }

    public Set<PerformanceCategoryServiceModel> getPerformanceCategories() {
        return performanceCategories;
    }

    public EditionDetailsServiceModel setPerformanceCategories(Set<PerformanceCategoryServiceModel> performanceCategories) {
        this.performanceCategories = performanceCategories;
        return this;
    }

    public Set<AgeGroupServiceModel> getAgeGroups() {
        return ageGroups;
    }

    public EditionDetailsServiceModel setAgeGroups(Set<AgeGroupServiceModel> ageGroups) {
        this.ageGroups = ageGroups;
        return this;
    }

    public Set<JuryMemberServiceModel> getJuryMembers() {
        return juryMembers;
    }

    public EditionDetailsServiceModel setJuryMembers(Set<JuryMemberServiceModel> juryMembers) {
        this.juryMembers = juryMembers;
        return this;
    }

    @Override
    public String toString() {
        return this.contestName + " - " + this.number + " Edition";
    }
}
