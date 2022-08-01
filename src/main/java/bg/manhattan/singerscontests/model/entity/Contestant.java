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
@Table(name = "contestants")
public class Contestant extends PersonBaseEntity {
    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(nullable = false, length = COUNTRY_MAX_LENGTH + 1)
    private String country;

    @NotBlank(message = "City name is required")
    @Size(max = CITY_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String city;

    @Size(max = INSTITUTION_MAX_LENGTH, message = "Should be shorter than {max} characters!")
    private String institution;

    @ManyToOne(optional = false)
    private Edition edition;

    @ManyToOne(optional = false)
    private AgeGroup ageGroup;

    @OneToMany(mappedBy = "contestant", cascade = CascadeType.ALL)
    private List<Song> songs;


    /**
     * The person who registered this contestant
     */

    @ManyToOne(cascade = CascadeType.PERSIST)
    private User registrar;

    private Integer scenarioNumber;

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

    public String getCountry() {
        return country;
    }

    public Contestant setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public Contestant setCity(String city) {
        this.city = city;
        return this;
    }

    public String getInstitution() {
        return institution;
    }

    public Contestant setInstitution(String institution) {
        this.institution = institution;
        return this;
    }

    public Contestant setRegistrar(User registrar) {
        this.registrar = registrar;
        return this;
    }

    public Integer getScenarioNumber() {
        return scenarioNumber;
    }

    public Contestant setScenarioNumber(Integer scenarioNumber) {
        this.scenarioNumber = scenarioNumber;
        return this;
    }
}
