package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.service.PerformanceCategoryServiceModel;

import javax.validation.constraints.*;
import java.time.Duration;

import static bg.manhattan.singerscontests.model.ModelConstants.NAME_MAX_LENGTH;

public class SongCreateBindingModel {

    private PerformanceCategoryViewModel category;

    @NotBlank(message = "Name name is required")
    private String name;


    @NotBlank(message = "Composer name is required")
    private String composerFullName;

    private String arrangerFullName;

    @NotBlank(message = "Lyricist name is required")
    private String lyricistFullName;

    @NotNull(message = "Required")
    @Min(value = 120, message = "Must be equal or greater than {value/60}")
    @Max(value = 240, message = "Must be equal or less than {value/60}")
    private Integer duration;

    @NotNull(message = "Instrumental is required")
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

    public Integer getDuration() {
        return duration;
    }

    public SongCreateBindingModel setDuration(Integer duration) {
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
