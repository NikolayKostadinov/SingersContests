package bg.manhattan.singerscontests.model.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@MappedSuperclass
public abstract class PersonBaseEntity extends BaseEntity{

    private static final int NAME_MAX_LENGTH = 20;

    @Column(name="first_name", length = NAME_MAX_LENGTH, nullable = false)
    private String firstName;

    @Column(name="middle_name", length = NAME_MAX_LENGTH, nullable = true)
    private String middleName;

    @Column(name="last_name", length = NAME_MAX_LENGTH, nullable = false)
    private String lastName;

    public String getFirstName() {
        return firstName;
    }

    public PersonBaseEntity setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getMiddleName() {
        return middleName;
    }

    public PersonBaseEntity setMiddleName(String middleName) {
        this.middleName = middleName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public PersonBaseEntity setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
