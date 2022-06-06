package bg.manhattan.singerscontests.model.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name="contestants")
public class Contestant extends PersonBaseEntity {

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

    public LocalDate getBirthDay() {
        return birthDay;
    }

    public Contestant setBirthDay(LocalDate birthDay) {
        this.birthDay = birthDay;
        return this;
    }
}
