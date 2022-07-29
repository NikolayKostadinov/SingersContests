package bg.manhattan.singerscontests.model.view;

public class EditionContestantViewModel {
    private String contestName;
    private Integer number;
    private String editionType;

    public String getContestName() {
        return contestName;
    }

    public EditionContestantViewModel setContestName(String contestName) {
        this.contestName = contestName;
        return this;
    }

    public Integer getNumber() {
        return number;
    }

    public EditionContestantViewModel setNumber(Integer number) {
        this.number = number;
        return this;
    }

    public String getEditionType() {
        return editionType;
    }

    public EditionContestantViewModel setEditionType(String editionType) {
        this.editionType = editionType;
        return this;
    }
}
