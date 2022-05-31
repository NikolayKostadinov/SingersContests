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

    @Column()
    private LocalDate dateOfBirth;
}
