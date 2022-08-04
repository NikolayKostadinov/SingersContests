package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.UserRole;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserRoleServiceModel;
import bg.manhattan.singerscontests.repositories.UserRoleRepository;
import bg.manhattan.singerscontests.services.UserRoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    private final UserRoleRepository userRoleRepository;
    private final ModelMapper mapper;

    public UserRoleServiceImpl(UserRoleRepository userRoleRepository, ModelMapper mapper) {
        this.userRoleRepository = userRoleRepository;
        this.mapper = mapper;
    }

    @Override
    public UserRoleServiceModel getRoleByName(UserRoleEnum userRole) {
        UserRole userRoleEntity = this.getRoleEntityByName(userRole);
        return this.mapper.map(userRoleEntity, UserRoleServiceModel.class);
    }

    @Override
    public UserRole getRoleEntityByName(UserRoleEnum role) {
        return this.userRoleRepository.findByUserRole(role)
                .orElseThrow(()->new NotFoundException("UserRole", role));
    }
}
