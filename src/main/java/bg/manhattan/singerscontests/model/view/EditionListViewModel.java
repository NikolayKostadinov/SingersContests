package bg.manhattan.singerscontests.model.view;

import bg.manhattan.singerscontests.model.enums.EditionType;

public class EditionListViewModel {
    private Long id;
    private Long contestId;
    private String contestName;
    private Integer number;
    private EditionType editionType;

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
}
