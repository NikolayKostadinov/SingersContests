package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.service.PerformanceCategoryServiceModel;

import java.time.Duration;

public class SongCreateBindingModel {

    private PerformanceCategoryViewModel category;
    private String name;

    private String composerFullName;

    private String arrangerFullName;

    private String lyricistFullName;

    private Duration duration;

    private String instrumentalUrl;

    public PerformanceCategoryViewModel getCategory() {
        return category;
    }

    public SongCreateBindingModel setCategory(PerformanceCategoryViewModel category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public SongCreateBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getComposerFullName() {
        return composerFullName;
    }

    public SongCreateBindingModel setComposerFullName(String composerFullName) {
        this.composerFullName = composerFullName;
        return this;
    }

    public String getArrangerFullName() {
        return arrangerFullName;
    }

    public SongCreateBindingModel setArrangerFullName(String arrangerFullName) {
        this.arrangerFullName = arrangerFullName;
        return this;
    }

    public String getLyricistFullName() {
        return lyricistFullName;
    }

    public SongCreateBindingModel setLyricistFullName(String lyricistFullName) {
        this.lyricistFullName = lyricistFullName;
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    public SongCreateBindingModel setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public String getInstrumentalUrl() {
        return instrumentalUrl;
    }

    public SongCreateBindingModel setInstrumentalUrl(String instrumentalUrl) {
        this.instrumentalUrl = instrumentalUrl;
        return this;
    }
}
