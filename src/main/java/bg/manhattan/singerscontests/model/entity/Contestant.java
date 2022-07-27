package bg.manhattan.singerscontests.model.entity;

import bg.manhattan.singerscontests.model.validators.UniqueUserName;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

@Entity
@Table(name="contestants")
public class Contestant extends PersonBaseEntity {

    @NotBlank(message = "First name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String firstName;

    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String middleName;

    @NotBlank(message = "Last name is required")
    @Size(max = NAME_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String lastName;

    @NotNull(message = "Username is required")
    @Size(min=USER_NAME_MIN_LENGTH,  max = USER_NAME_MAX_LENGTH,
            message = "User name length must be between {min} and {max} characters!")
    @UniqueUserName
    private String username;

    @Column(name="image_url", nullable = false)
    private String imageUrl;

    @ManyToOne(optional = false)
    private Edition edition;

    @ManyToOne(optional = false)
    private AgeGroup ageGroup;

    @OneToMany(mappedBy="contestant")
    private List<Song> songs;

    @Column(name="birth_day")
    private LocalDate birthDay;

    /**
     * The person who registered this contestant
     */
    @ManyToOne
    private User registrar;

    public String getImageUrl() {
        return imageUrl;
    }

    public Contestant setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public Edition getEdition() {
        return edition;
    }

    public Contestant setEdition(Edition edition) {
        this.edition = edition;
        return this;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public Contestant setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public Contestant setSongs(List<Song> songs) {
        this.songs = songs;
        return this;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Contestant setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }

    public User getRegistrar() {
        return registrar;
    }

    public Contestant setRegistrar(User registrar) {
        this.registrar = registrar;
        return this;
    }
}
