package bg.manhattan.singerscontests.model.binding;

import bg.manhattan.singerscontests.model.validators.ExistingAgeGroup;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

@ExistingAgeGroup(editionId = "editionId", birthDate = "birthDay", message = "Contestant age is out of scope!")
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
    @Past
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDay;

    @NotBlank(message = "Country name is required")
    @Size(max = COUNTRY_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String country;

    @NotBlank(message = "City name is required")
    @Size(max = CITY_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String city;

    @Size(max = INSTITUTION_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String institution;

    @NotNull
    private List<@Valid SongCreateBindingModel> songs;

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

    public String getCountry() {
        return country;
    }

    public ContestantCreateBindingModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ContestantCreateBindingModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getInstitution() {
        return institution;
    }

    public ContestantCreateBindingModel setInstitution(String institution) {
        this.institution = institution;
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
