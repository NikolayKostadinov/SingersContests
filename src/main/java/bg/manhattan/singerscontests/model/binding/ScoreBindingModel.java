package bg.manhattan.singerscontests.model.binding;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class ScoreBindingModel {

    private Long id;

    @NotNull(message = "Edition is required!")
    private Long editionId;
    @NotNull(message = "Song is required!")
    private Long songId;

    @NotNull(message = "Field is required!")
    @Positive(message="Must be positive!")
    @DecimalMin("1")
    @DecimalMax("10")
    private BigDecimal artistry;

    @NotNull(message = "Field is required!")
    @Positive(message="Must be positive!")
    @DecimalMin("1")
    @DecimalMax("10")
    private BigDecimal intonation;

    @NotNull(message = "Field is required!")
    @Positive(message="Must be positive!")
    @DecimalMin("1")
    @DecimalMax("10")
    private BigDecimal repertoire;

    public Long getId() {
        return id;
    }

    public ScoreBindingModel setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getEditionId() {
        return editionId;
    }

    public ScoreBindingModel setEditionId(Long editionId) {
        this.editionId = editionId;
        return this;
    }

    public Long getSongId() {
        return songId;
    }

    public ScoreBindingModel setSongId(Long songId) {
        this.songId = songId;
        return this;
    }

    public BigDecimal getArtistry() {
        return artistry;
    }

    public ScoreBindingModel setArtistry(BigDecimal artistry) {
        this.artistry = artistry;
        return this;
    }

    public BigDecimal getIntonation() {
        return intonation;
    }

    public ScoreBindingModel setIntonation(BigDecimal intonation) {
        this.intonation = intonation;
        return this;
    }

    public BigDecimal getRepertoire() {
        return repertoire;
    }

    public ScoreBindingModel setRepertoire(BigDecimal repertoire) {
        this.repertoire = repertoire;
        return this;
    }
}
