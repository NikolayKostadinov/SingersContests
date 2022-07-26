package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.enums.EditionType;

import java.time.LocalDate;

public class EditionListViewModel {
    private Long id;

    private Long contestId;

    private String contestName;

    private Integer number;

    private EditionType editionType;

    private LocalDate beginDate;

    private LocalDate endDate;

    public Long getId() {
        return id;
    }

    public EditionListViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getContestId() {
        return contestId;
    }

    public EditionListViewModel setContestId(Long contestId) {
        this.contestId = contestId;
        return this;
    }

    public String getContestName() {
        return contestName;
    }

    public EditionListViewModel setContestName(String contestName) {
        this.contestName = contestName;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public EditionListViewModel setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public EditionListViewModel setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public LocalDate getBeginDate() {
        return beginDate;
    }

    public EditionListViewModel setBeginDate(LocalDate beginDate) {
        this.beginDate = beginDate;
        return this;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public EditionListViewModel setEndDate(LocalDate endDate) {
        this.endDate = endDate;
        return this;
    }
}
