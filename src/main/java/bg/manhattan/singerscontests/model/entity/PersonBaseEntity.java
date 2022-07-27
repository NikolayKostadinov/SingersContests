package bg.manhattan.singerscontests.model.entity;

import bg.manhattan.singerscontests.model.IHaveNames;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.time.LocalDate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static bg.manhattan.singerscontests.model.ModelConstants.NAME_MAX_LENGTH;

@MappedSuperclass
public abstract class PersonBaseEntity extends BaseEntity implements IHaveNames{

    @Column(name="first_name", length = NAME_MAX_LENGTH, nullable = false)
    private String firstName;

    @Column(name="middle_name", length = NAME_MAX_LENGTH, nullable = true)
    private String middleName;

    @Column(name="last_name", length = NAME_MAX_LENGTH, nullable = false)
    private String lastName;

    @Transient
    private String fullName;

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


    public String getFullName() {
        if (this.fullName == null) {
            this.fullName = Stream.of(this.getFirstName(), this.getMiddleName(), this.getLastName())
                    .filter(n -> n != null && !n.isEmpty())
                    .collect(Collectors.joining(" "));
        }
        return this.fullName;
    }
}
