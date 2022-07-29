package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.service.SongServiceModel;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static bg.manhattan.singerscontests.model.ModelConstants.NAME_MAX_LENGTH;

public class ContestantCreateBindingModel {
    @NotBlank(message = "First name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String firstName;

    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String lastName;

    @NotBlank(message = "Picture is required!")
    private String imageUrl;

    @NotNull(message = "Edition is required!")
    private Long editionId;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @NotNull
    private List<SongCreateBindingModel> songs;

    public ContestantCreateBindingModel() {
        this.songs = new ArrayList<>();
    }

    public String getFirstName() {
        return firstName;
    }

    public ContestantCreateBindingModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ContestantCreateBindingModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ContestantCreateBindingModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ContestantCreateBindingModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Long getEditionId() {
        return editionId;
    }

    public ContestantCreateBindingModel setEditionId(Long editionId) {
        this.editionId = editionId;
        return this;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public ContestantCreateBindingModel setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public List<SongCreateBindingModel> getSongs() {
        return songs;
    }

    public ContestantCreateBindingModel setSongs(List<SongCreateBindingModel> songs) {
        this.songs = songs;
        return this;
    }
}
