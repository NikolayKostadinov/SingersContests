package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.entity.UserRole;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;

public interface RoleService {
    UserRole getRoleByName(UserRoleEnum juryMember);
}
