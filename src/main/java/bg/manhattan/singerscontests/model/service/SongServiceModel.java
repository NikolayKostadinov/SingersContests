package bg.manhattan.singerscontests.model.service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Set;

public class SongServiceModel {

    private Long id;

    private PerformanceCategoryServiceModel category;

    private String name;

    private String composerFullName;

    private String arrangerFullName;

    private String lyricistFullName;

    private Integer duration;

    private String instrumentalUrl;

    public Long getId() {
        return id;
    }

    public Set<RaringServiceModel> ratings;

    public SongServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public PerformanceCategoryServiceModel getCategory() {
        return category;
    }

    public SongServiceModel setCategory(PerformanceCategoryServiceModel category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public SongServiceModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getComposerFullName() {
        return composerFullName;
    }

    public SongServiceModel setComposerFullName(String composerFullName) {
        this.composerFullName = composerFullName;
        return this;
    }

    public String getArrangerFullName() {
        return arrangerFullName;
    }

    public SongServiceModel setArrangerFullName(String arrangerFullName) {
        this.arrangerFullName = arrangerFullName;
        return this;
    }

    public String getLyricistFullName() {
        return lyricistFullName;
    }

    public SongServiceModel setLyricistFullName(String lyricistFullName) {
        this.lyricistFullName = lyricistFullName;
        return this;
    }

    public Integer getDuration() {
        return duration;
    }

    public SongServiceModel setDuration(Integer duration) {
        this.duration = duration;
        return this;
    }

    public String getInstrumentalUrl() {
        return instrumentalUrl;
    }

    public SongServiceModel setInstrumentalUrl(String instrumentalUrl) {
        this.instrumentalUrl = instrumentalUrl;
        return this;
    }

    public Set<RaringServiceModel> getRatings() {
        return ratings;
    }

    public SongServiceModel setRatings(Set<RaringServiceModel> ratings) {
        this.ratings = ratings;
        return this;
    }

    public BigDecimal getRatingByJuryId(Long juryId){
        return this.getRatings()
                .stream()
                .filter(r->r.getJuryMember().getId().equals(juryId))
                .findFirst()
                .orElse(new RaringServiceModel())
                .getAverageScore();
    }

    public BigDecimal getAvgRating(){
        if (ratings.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return this.getRatings()
                .stream()
                .map(RaringServiceModel::getAverageScore)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(this.getRatings().size()), MathContext.DECIMAL128);
    }
}
