package bg.manhattan.singerscontests.services;


import bg.manhattan.singerscontests.exceptions.PasswordNotMatchesException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceProfileDetailsModel;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {

    void register(UserRegisterBindingModel registerDto);

    User getCurrentUser(Principal principal) throws UserNotFoundException;

    void deleteUser(String username, String password) throws UserNotFoundException, PasswordNotMatchesException;

    void changeUserPassword(String username, String oldPassword, String newPassword) throws UserNotFoundException,
            PasswordNotMatchesException;

    void changeUserProfileDetails(String username, UserServiceProfileDetailsModel userModel) throws UserNotFoundException;

    Optional<UserServiceModel> getUserByEmail(String email);

    Optional<UserServiceModel> getUserByUsername(String userName);

    List<UserServiceModel> getUsersByRole(UserRoleEnum contestManager);

    void changeUserEmail(String name, String newEmail) throws UserNotFoundException;

    User getUserByRoleAndId(UserRoleEnum contestManager, Long managerId) throws UserNotFoundException;
}
