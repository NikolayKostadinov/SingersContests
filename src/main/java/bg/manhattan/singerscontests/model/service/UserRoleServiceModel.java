package bg.manhattan.singerscontests.model.service;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;

public class UserRoleServiceModel {
    private Long id;
    private UserRoleEnum userRole;

    public Long getId() {
        return id;
    }

    public UserRoleServiceModel setId(Long id) {
        this.id = id;
        return this;
    }

    public UserRoleEnum getUserRole() {
        return userRole;
    }

    public UserRoleServiceModel setUserRole(UserRoleEnum userRole) {
        this.userRole = userRole;
        return this;
    }
}
