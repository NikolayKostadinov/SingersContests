package bg.manhattan.singerscontests.services;


import bg.manhattan.singerscontests.exceptions.PasswordNotMatchesException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceProfileDetailsModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

public interface UserService {
    void register(UserServiceModel model, Locale locale) throws MessagingException;

    User getCurrentUser(Principal principal) ;

    void deleteUser(String username, String password) throws UserNotFoundException, PasswordNotMatchesException;

    void changeUserPassword(String username, String oldPassword, String newPassword) throws UserNotFoundException,
            PasswordNotMatchesException;

    void changeUserProfileDetails(String username, UserServiceProfileDetailsModel userModel) throws UserNotFoundException;

    Optional<UserServiceModel> getUserByEmail(String email);

    Optional<UserServiceModel> getUserByUsername(String userName);

    List<UserServiceModel> getUsersByRole(UserRoleEnum contestManager);

    void changeUserEmail(String username, String newEmail, Locale locale) throws UserNotFoundException;

    User getUserByRoleAndId(UserRoleEnum contestManager, Long managerId) throws UserNotFoundException;

    List<UserServiceModel> getPotentialJuryMembers(UserRoleEnum role);
}
