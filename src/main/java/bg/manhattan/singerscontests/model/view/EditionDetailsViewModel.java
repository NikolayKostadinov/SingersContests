package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.enums.EditionType;

import java.time.LocalDate;
import java.util.Set;

public class EditionDetailsViewModel {
    private Long id;

    private Long contestId;

    private String contestName;

    private Integer number;

    private EditionType editionType;

    private LocalDate beginDate;

    private LocalDate endDate;

    private LocalDate beginOfSubscriptionDate;

    private LocalDate endOfSubscriptionDate;

    private String regulations;

    private Set<JuryDetailsViewModel> juryMembers;

    public Long getId() {
        return id;
    }

    public EditionDetailsViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getContestId() {
        return contestId;
    }

    public EditionDetailsViewModel setContestId(Long contestId) {
        this.contestId = contestId;
        return this;
    }

    public String getContestName() {
        return contestName;
    }

    public EditionDetailsViewModel setContestName(String contestName) {
        this.contestName = contestName;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public EditionDetailsViewModel setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public EditionDetailsViewModel setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public EditionDetailsViewModel setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EditionDetailsViewModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }

    public LocalDate getBeginOfSubscriptionDate() {
        return beginOfSubscriptionDate;
    }

    public EditionDetailsViewModel setBeginOfSubscriptionDate(LocalDate beginOfSubscriptionDate) {
        this.beginOfSubscriptionDate = beginOfSubscriptionDate;
        return this;
    }

    public LocalDate getEndOfSubscriptionDate() {
        return endOfSubscriptionDate;
    }

    public EditionDetailsViewModel setEndOfSubscriptionDate(LocalDate endOfSubscriptionDate) {
        this.endOfSubscriptionDate = endOfSubscriptionDate;
        return this;
    }

    public String getRegulations() {
        return regulations;
    }

    public EditionDetailsViewModel setRegulations(String regulations) {
        this.regulations = regulations;
        return this;
    }

    public Set<JuryDetailsViewModel> getJuryMembers() {
        return juryMembers;
    }

    public EditionDetailsViewModel setJuryMembers(Set<JuryDetailsViewModel> juryMembers) {
        this.juryMembers = juryMembers;
        return this;
    }
}
