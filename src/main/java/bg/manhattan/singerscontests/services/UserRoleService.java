package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.UserRole;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserRoleServiceModel;

public interface UserRoleService {
    UserRoleServiceModel getRoleByName(UserRoleEnum contestManager);

    UserRole getRoleEntityByName(UserRoleEnum role);
}
