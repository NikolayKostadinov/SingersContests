package bg.manhattan.singerscontests.model.service;

import java.time.Duration;

public class SongServiceModel {

    private Long id;

    private PerformanceCategoryServiceModel category;

    private String name;

    private String composerFullName;

    private String arrangerFullName;

    private String lyricistFullName;

    private Duration duration;

    private String instrumentalUrl;

    public Long getId() {
        return id;
    }

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

    public Duration getDuration() {
        return duration;
    }

    public SongServiceModel setDuration(Duration duration) {
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
}
