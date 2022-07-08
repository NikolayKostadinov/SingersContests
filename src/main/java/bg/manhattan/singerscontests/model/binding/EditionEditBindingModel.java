package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.enums.EditionType;
import bg.manhattan.singerscontests.model.validators.GreaterThanOrEqual;
import bg.manhattan.singerscontests.model.validators.UniqueEdition;
import bg.manhattan.singerscontests.model.validators.UniqueInOthersEdition;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@GreaterThanOrEqual(first = "beginDate", second = "endDate", message = "End date must be after begin date!")
@GreaterThanOrEqual(first = "beginOfSubscriptionDate", second = "endOfSubscriptionDate", message = "End date must be after begin date!")
@GreaterThanOrEqual(first = "beginOfSubscriptionDate", second = "beginDate", message = "The beginning of subscription must be before beginning of the contest!")
//@UniqueInOthersEdition(numberField = "number",  contestIdField = "contestId", message = "Number must be unique")
public class EditionEditBindingModel {
    @NotNull
    private Long id;

    @NotNull
    private Integer number;

    @NotNull(message = "Please choose a valid edition type")
    private EditionType editionType;

    @NotNull(message = "Please choose a valid age calculation type")
    private AgeCalculationType ageCalculationType;

    @FutureOrPresent(message = "Date must be in future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginDate;

    @FutureOrPresent(message = "Date must be in future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @FutureOrPresent(message = "Date must be in future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate beginOfSubscriptionDate;

    @FutureOrPresent(message = "Date must be in future!")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endOfSubscriptionDate;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String regulations;
    private Long contestId;

    private String contestName;

    @NotEmpty(message = "There must be at least one age category!")
    private List<@Valid PerformanceCategoryBindingModel> performanceCategories;

    @NotEmpty(message = "There must be at least one age group!")
    private List<@Valid AgeGroupBindingModel> ageGroups;

    @NotNull(message = "There must be at least one jury member!")
    @NotEmpty(message = "There must be at least one jury member!")
    private Set<@NotNull(message = "Please select valid jury member!") @Valid Long> juryMembers;

    public EditionEditBindingModel() {
        this.performanceCategories = new ArrayList<>();
        this.ageGroups = new ArrayList<>();
        this.juryMembers = new HashSet<>();
    }

    public Long getId() {
        return id;
    }

    public EditionEditBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public EditionEditBindingModel setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public EditionEditBindingModel setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public AgeCalculationType getAgeCalculationType() {
        return ageCalculationType;
    }

    public EditionEditBindingModel setAgeCalculationType(AgeCalculationType ageCalculationType) {
        this.ageCalculationType = ageCalculationType;
        return this;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public EditionEditBindingModel setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EditionEditBindingModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public LocalDate getBeginOfSubscriptionDate() {
        return beginOfSubscriptionDate;
    }

    public EditionEditBindingModel setBeginOfSubscriptionDate(LocalDate beginOfSubscriptionDate) {
        this.beginOfSubscriptionDate = beginOfSubscriptionDate;
        return this;
    }

    public LocalDate getEndOfSubscriptionDate() {
        return endOfSubscriptionDate;
    }

    public EditionEditBindingModel setEndOfSubscriptionDate(LocalDate endOfSubscriptionDate) {
        this.endOfSubscriptionDate = endOfSubscriptionDate;
        return this;
    }

    public String getRegulations() {
        return regulations;
    }

    public EditionEditBindingModel setRegulations(String regulations) {
        this.regulations = regulations;
        return this;
    }

    public Long getContestId() {
        return contestId;
    }

    public EditionEditBindingModel setContestId(Long contestId) {
        this.contestId = contestId;
        return this;
    }

    public String getContestName() {
        return contestName;
    }

    public EditionEditBindingModel setContestName(String contestName) {
        this.contestName = contestName;
        return this;
    }

    public List<PerformanceCategoryBindingModel> getPerformanceCategories() {
        return performanceCategories;
    }

    public EditionEditBindingModel setPerformanceCategories(List<PerformanceCategoryBindingModel> performanceCategories) {
        this.performanceCategories = performanceCategories;
        return this;
    }

    public List<AgeGroupBindingModel> getAgeGroups() {
        return ageGroups;
    }

    public EditionEditBindingModel setAgeGroups(List<AgeGroupBindingModel> ageGroups) {
        this.ageGroups = ageGroups;
        return this;
    }

    public Set<Long> getJuryMembers() {
        return juryMembers;
    }

    public EditionEditBindingModel setJuryMembers(Set<Long> juryMembers) {
        this.juryMembers = juryMembers;
        return this;
    }
}
