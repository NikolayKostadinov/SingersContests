package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="songs")
public class Song extends BaseEntity {

    @ManyToOne(optional = false)
    private PerformanceCategory category;

    @ManyToOne(optional = false)
    private Contestant contestant;

    @Column(length = 101, nullable = false)
    private String name;

    @Column(name="composer_full_name", length = 101, nullable = false)
    private String composerFullName;

    @Column(name="arranger_full_name", length = 101)
    private String arrangerFullName;

    @Column(name="lyricist_full_name", length = 101, nullable = false)
    private String lyricistFullName;

    @Column(nullable = false)
    private Integer duration;

    @Column(length = 4097, nullable = false)
    private String instrumentalUrl;

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL)
    private Set<Rating> ratings;

    public Song() {
        this.ratings = new HashSet<>();
    }

    public PerformanceCategory getCategory() {
        return category;
    }

    public Song setCategory(PerformanceCategory category) {
        this.category = category;
        return this;
    }

    public Contestant getContestant() {
        return contestant;
    }

    public Song setContestant(Contestant contestant) {
        this.contestant = contestant;
        return this;
    }

    public String getName() {
        return name;
    }

    public Song setName(String name) {
        this.name = name;
        return this;
    }

    public String getComposerFullName() {
        return composerFullName;
    }

    public Song setComposerFullName(String composerFullName) {
        this.composerFullName = composerFullName;
        return this;
    }

    public String getArrangerFullName() {
        return arrangerFullName;
    }

    public Song setArrangerFullName(String arrangerFullName) {
        this.arrangerFullName = arrangerFullName;
        return this;
    }

    public String getLyricistFullName() {
        return lyricistFullName;
    }

    public Song setLyricistFullName(String lyricistFullName) {
        this.lyricistFullName = lyricistFullName;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public Song setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getInstrumentalUrl() {
        return instrumentalUrl;
    }

    public Song setInstrumentalUrl(String instrumentalUrl) {
        this.instrumentalUrl = instrumentalUrl;
        return this;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public Song setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
        return this;
    }
}
