package bg.manhattan.singerscontests.model.service;

import java.math.BigDecimal;

public class RaringServiceModel {

    private BigDecimal artistry;

    private BigDecimal intonation;

    private BigDecimal repertoire;

    private BigDecimal averageScore;

    private UserServiceModel juryMember;

    public BigDecimal getArtistry() {
        return artistry;
    }

    public RaringServiceModel setArtistry(BigDecimal artistry) {
        this.artistry = artistry;
        return this;
    }

    public BigDecimal getIntonation() {
        return intonation;
    }

    public RaringServiceModel setIntonation(BigDecimal intonation) {
        this.intonation = intonation;
        return this;
    }

    public BigDecimal getRepertoire() {
        return repertoire;
    }

    public RaringServiceModel setRepertoire(BigDecimal repertoire) {
        this.repertoire = repertoire;
        return this;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public RaringServiceModel setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
        return this;
    }

    public UserServiceModel getJuryMember() {
        return juryMember;
    }

    public RaringServiceModel setJuryMember(UserServiceModel juryMember) {
        this.juryMember = juryMember;
        return this;
    }
}
