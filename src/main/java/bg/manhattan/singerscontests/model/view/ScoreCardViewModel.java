package bg.manhattan.singerscontests.model.view;

import java.math.BigDecimal;

public class ScoreCardViewModel {
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

    public Long getId() {
        return id;
    }

    public ScoreCardViewModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getEditionId() {
        return editionId;
    }

    public ScoreCardViewModel setEditionId(Long editionId) {
        this.editionId = editionId;
        return this;
    }

    public String getContestantFullName() {
        return contestantFullName;
    }

    public ScoreCardViewModel setContestantFullName(String contestantFullName) {
        this.contestantFullName = contestantFullName;
        return this;
    }

    public String getContestantImageUrl() {
        return contestantImageUrl;
    }

    public ScoreCardViewModel setContestantImageUrl(String contestantImageUrl) {
        this.contestantImageUrl = contestantImageUrl;
        return this;
    }

    public Long getSongId() {
        return songId;
    }

    public ScoreCardViewModel setSongId(Long songId) {
        this.songId = songId;
        return this;
    }

    public String getSongName() {
        return songName;
    }

    public ScoreCardViewModel setSongName(String songName) {
        this.songName = songName;
        return this;
    }

    public String getSongComposerFullName() {
        return songComposerFullName;
    }

    public ScoreCardViewModel setSongComposerFullName(String songComposerFullName) {
        this.songComposerFullName = songComposerFullName;
        return this;
    }

    public String getSongArrangerFullName() {
        return songArrangerFullName;
    }

    public ScoreCardViewModel setSongArrangerFullName(String songArrangerFullName) {
        this.songArrangerFullName = songArrangerFullName;
        return this;
    }

    public String getSongLyricistFullName() {
        return songLyricistFullName;
    }

    public ScoreCardViewModel setSongLyricistFullName(String songLyricistFullName) {
        this.songLyricistFullName = songLyricistFullName;
        return this;
    }

    public BigDecimal getArtistry() {
        return artistry;
    }

    public ScoreCardViewModel setArtistry(BigDecimal artistry) {
        this.artistry = artistry;
        return this;
    }

    public BigDecimal getIntonation() {
        return intonation;
    }

    public ScoreCardViewModel setIntonation(BigDecimal intonation) {
        this.intonation = intonation;
        return this;
    }

    public BigDecimal getRepertoire() {
        return repertoire;
    }

    public ScoreCardViewModel setRepertoire(BigDecimal repertoire) {
        this.repertoire = repertoire;
        return this;
    }
}
