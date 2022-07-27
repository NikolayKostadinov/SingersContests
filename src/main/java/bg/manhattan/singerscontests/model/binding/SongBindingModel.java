package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.service.PerformanceCategoryServiceModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.Duration;

public class SongBindingModel {
    @NotNull
    private PerformanceCategoryServiceModel category;

    @NotBlank
    private String name;

    @NotBlank
    @Size(max = 100)
    private String composerFullName;

    @Size(max = 100)
    private String arrangerFullName;

    @NotBlank
    @Size(max = 100)
    private String lyricistFullName;

    @NotNull
    private Duration duration;

    @NotBlank
    @Size(max = 4096)
    private String instrumentalUrl;

    public PerformanceCategoryServiceModel getCategory() {
        return category;
    }

    public SongBindingModel setCategory(PerformanceCategoryServiceModel category) {
        this.category = category;
        return this;
    }

    public String getName() {
        return name;
    }

    public SongBindingModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getComposerFullName() {
        return composerFullName;
    }

    public SongBindingModel setComposerFullName(String composerFullName) {
        this.composerFullName = composerFullName;
        return this;
    }

    public String getArrangerFullName() {
        return arrangerFullName;
    }

    public SongBindingModel setArrangerFullName(String arrangerFullName) {
        this.arrangerFullName = arrangerFullName;
        return this;
    }

    public String getLyricistFullName() {
        return lyricistFullName;
    }

    public SongBindingModel setLyricistFullName(String lyricistFullName) {
        this.lyricistFullName = lyricistFullName;
        return this;
    }

    public Duration getDuration() {
        return duration;
    }

    public SongBindingModel setDuration(Duration duration) {
        this.duration = duration;
        return this;
    }

    public String getInstrumentalUrl() {
        return instrumentalUrl;
    }

    public SongBindingModel setInstrumentalUrl(String instrumentalUrl) {
        this.instrumentalUrl = instrumentalUrl;
        return this;
    }
}
