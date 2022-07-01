package bg.manhattan.singerscontests.services;


import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.util.CurrentUser;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

public interface UserService {

    void register(UserRegisterBindingModel registerDto);

    User getCurrentUser(Principal principal) throws UserNotFoundException;

    Optional<UserServiceModel> getUserByEmail(String email);

    Optional<UserServiceModel> getUserByUsername(String userName);

    List<UserServiceModel> getUsersByRole(UserRoleEnum contestManager);
}
