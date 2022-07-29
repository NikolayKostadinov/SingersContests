package bg.manhattan.singerscontests.model.service;

import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.enums.EditionType;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

public class ContestantServiceModel  {
    private Long id;

    private String firstName;

    private String middleName;

    private String lastName;

    private String fullName;

    private String imageUrl;


    private String country;


    private String city;


    private String institution;

    private Long editionId;

    private Integer editionNumber;

    private EditionType editionType;

    private String contestName;

    private AgeGroup ageGroup;

    private List<SongServiceModel> songs;

    private LocalDate birthDay;

    public ContestantServiceModel() {
        this.songs = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public ContestantServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public String getFirstName() {
        return firstName;
    }

    public ContestantServiceModel setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public ContestantServiceModel setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public ContestantServiceModel setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public String getFullName() {
        return fullName;
    }

    public ContestantServiceModel setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public ContestantServiceModel setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public ContestantServiceModel setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getCity() {
        return city;
    }

    public ContestantServiceModel setCity(String city) {
        this.city = city;
        return this;
    }

    public String getInstitution() {
        return institution;
    }

    public ContestantServiceModel setInstitution(String institution) {
        this.institution = institution;
        return this;
    }

    public Long getEditionId() {
        return editionId;
    }

    public ContestantServiceModel setEditionId(Long editionId) {
        this.editionId = editionId;
        return this;
    }

    public Integer getEditionNumber() {
        return editionNumber;
    }

    public ContestantServiceModel setEditionNumber(Integer editionNumber) {
        this.editionNumber = editionNumber;
        return this;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public ContestantServiceModel setEditionType(EditionType editionType) {
        this.editionType = editionType;
        return this;
    }

    public String getContestName() {
        return contestName;
    }

    public ContestantServiceModel setContestName(String contestName) {
        this.contestName = contestName;
        return this;
    }

    public AgeGroup getAgeGroup() {
        return ageGroup;
    }

    public ContestantServiceModel setAgeGroup(AgeGroup ageGroup) {
        this.ageGroup = ageGroup;
        return this;
    }

    public List<SongServiceModel> getSongs() {
        return songs;
    }

    public ContestantServiceModel setSongs(List<SongServiceModel> songs) {
        this.songs = songs;
        return this;
    }

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public ContestantServiceModel setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }
}
