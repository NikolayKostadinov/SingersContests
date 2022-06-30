package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="contestants")
public class Contestant extends PersonBaseEntity {

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
