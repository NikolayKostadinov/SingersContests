package bg.manhattan.singerscontests.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "ratings")
public class Rating extends BaseEntity {

    @Column
    private BigDecimal artistry;
    @Column
    private BigDecimal intonation;
    @Column
    private BigDecimal repertoire;

    @Column(name = "average_score")
    private BigDecimal averageScore;
    @ManyToOne
    private Song song;

    @ManyToOne
    private User juryMember;

    public BigDecimal getArtistry() {
        return artistry;
    }

    public Rating setArtistry(BigDecimal artistry) {
        this.artistry = artistry;
        return this;
    }

    public BigDecimal getIntonation() {
        return intonation;
    }

    public Rating setIntonation(BigDecimal intonation) {
        this.intonation = intonation;
        return this;
    }

    public BigDecimal getRepertoire() {
        return repertoire;
    }

    public Rating setRepertoire(BigDecimal repertoire) {
        this.repertoire = repertoire;
        return this;
    }

    public BigDecimal getAverageScore() {
        return averageScore;
    }

    public Rating setAverageScore(BigDecimal averageScore) {
        this.averageScore = averageScore;
        return this;
    }

    public Song getSong() {
        return song;
    }

    public Rating setSong(Song song) {
        this.song = song;
        return this;
    }

    public User getJuryMember() {
        return juryMember;
    }

    public Rating setJuryMember(User juryMember) {
        this.juryMember = juryMember;
        return this;
    }
}
