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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRole)) return false;

        UserRole userRole1 = (UserRole) o;

        return getUserRole() == userRole1.getUserRole();
    }

    @Override
    public int hashCode() {
        return getUserRole() != null ? getUserRole().hashCode() : 0;
    }
}
