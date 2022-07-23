package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.UserRole;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.repositories.UserRoleRepository;
import bg.manhattan.singerscontests.services.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {
    private final UserRoleRepository userRoleRepository;

    public RoleServiceImpl(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserRole getRoleByName(UserRoleEnum role) {
        return this.userRoleRepository.findByUserRole(role)
                .orElseThrow(()->new NotFoundException("UserRole", role));
    }
}
