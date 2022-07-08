package bg.manhattan.singerscontests.model.service;

import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.enums.EditionType;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class EditionServiceModel {

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
    private Set<Long> juryMembers;

    public EditionServiceModel() {
        this.performanceCategories = new HashSet<>();
        this.ageGroups = new HashSet<>();
        this.juryMembers = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public EditionServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public EditionServiceModel setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public EditionServiceModel setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public AgeCalculationType getAgeCalculationType() {
        return ageCalculationType;
    }

    public EditionServiceModel setAgeCalculationType(AgeCalculationType ageCalculationType) {
        this.ageCalculationType = ageCalculationType;
        return this;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public EditionServiceModel setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EditionServiceModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public LocalDate getBeginOfSubscriptionDate() {
        return beginOfSubscriptionDate;
    }

    public EditionServiceModel setBeginOfSubscriptionDate(LocalDate beginOfSubscriptionDate) {
        this.beginOfSubscriptionDate = beginOfSubscriptionDate;
        return this;
    }

    public LocalDate getEndOfSubscriptionDate() {
        return endOfSubscriptionDate;
    }

    public EditionServiceModel setEndOfSubscriptionDate(LocalDate endOfSubscriptionDate) {
        this.endOfSubscriptionDate = endOfSubscriptionDate;
        return this;
    }

    public String getRegulations() {
        return regulations;
    }

    public EditionServiceModel setRegulations(String regulations) {
        this.regulations = regulations;
        return this;
    }

    public Long getContestId() {
        return contestId;
    }

    public EditionServiceModel setContestId(Long contestId) {
        this.contestId = contestId;
        return this;
    }

    public String getContestName() {
        return contestName;
    }

    public EditionServiceModel setContestName(String contestName) {
        this.contestName = contestName;
        return this;
    }

    public Set<PerformanceCategoryServiceModel> getPerformanceCategories() {
        return performanceCategories;
    }

    public EditionServiceModel setPerformanceCategories(Set<PerformanceCategoryServiceModel> performanceCategories) {
        this.performanceCategories = performanceCategories;
        return this;
    }

    public Set<AgeGroupServiceModel> getAgeGroups() {
        return ageGroups;
    }

    public EditionServiceModel setAgeGroups(Set<AgeGroupServiceModel> ageGroups) {
        this.ageGroups = ageGroups;
        return this;
    }

    public Set<Long> getJuryMembers() {
        return juryMembers;
    }

    public EditionServiceModel setJuryMembers(Set<Long> juryMembers) {
        this.juryMembers = juryMembers;
        return this;
    }
}
