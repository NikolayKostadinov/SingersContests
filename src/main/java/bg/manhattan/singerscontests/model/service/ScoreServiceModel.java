package bg.manhattan.singerscontests.model.service;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class ScoreServiceModel {
    private Long id;
    private Long editionId;
    private String contestantFullName;
    private String contestantImageUrl;
    private Long songId;
    private String songName;
    private String songComposerFullName;
    private String songArrangerFullName;
    private String songLyricistFullName;
    private BigDecimal artistry;
    private BigDecimal intonation;
    private BigDecimal repertoire;

    private BigDecimal averageScore;

    public Long getId() {
        return id;
    }

    public ScoreServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getEditionId() {
        return editionId;
    }

    public ScoreServiceModel setEditionId(Long editionId) {
        this.editionId = editionId;
        return this;
    }

    public String getContestantFullName() {
        return contestantFullName;
    }

    public ScoreServiceModel setContestantFullName(String contestantFullName) {
        this.contestantFullName = contestantFullName;
        return this;
    }

    public String getContestantImageUrl() {
        return contestantImageUrl;
    }

    public ScoreServiceModel setContestantImageUrl(String contestantImageUrl) {
        this.contestantImageUrl = contestantImageUrl;
        return this;
    }

    public Long getSongId() {
        return songId;
    }

    public ScoreServiceModel setSongId(Long songId) {
        this.songId = songId;
        return this;
    }

    public String getSongName() {
        return songName;
    }

    public ScoreServiceModel setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getSongComposerFullName() {
        return songComposerFullName;
    }

    public ScoreServiceModel setSongComposerFullName(String songComposerFullName) {
        this.songComposerFullName = songComposerFullName;
        return this;
    }

    public String getSongArrangerFullName() {
        return songArrangerFullName;
    }

    public ScoreServiceModel setSongArrangerFullName(String songArrangerFullName) {
        this.songArrangerFullName = songArrangerFullName;
        return this;
    }

    public String getSongLyricistFullName() {
        return songLyricistFullName;
    }

    public ScoreServiceModel setSongLyricistFullName(String songLyricistFullName) {
        this.songLyricistFullName = songLyricistFullName;
        return this;
    }

    public BigDecimal getArtistry() {
        return artistry;
    }

    public ScoreServiceModel setArtistry(BigDecimal artistry) {
        this.artistry = artistry;
        return this;
    }

    public BigDecimal getIntonation() {
        return intonation;
    }

    public ScoreServiceModel setIntonation(BigDecimal intonation) {
        this.intonation = intonation;
        return this;
    }

    public BigDecimal getRepertoire() {
        return repertoire;
    }

    public ScoreServiceModel setRepertoire(BigDecimal repertoire) {
        this.repertoire = repertoire;
        return this;
    }

    public BigDecimal getAverageScore() {
        if (averageScore == null
                && this.artistry != null
                && this.intonation != null
                && this.repertoire != null) {
            this.averageScore = this.artistry
                    .add(this.intonation)
                    .add(this.repertoire)
                    .divide(BigDecimal.valueOf(3), RoundingMode.HALF_UP);
        }
        return averageScore;
    }

    public ScoreServiceModel setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
        return this;
    }
}
