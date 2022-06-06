package bg.manhattan.singerscontests.model.entity;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity{
    @Enumerated(EnumType.STRING)
    private UserRoleEnum userRole;

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserRole setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }
}
