package bg.manhattan.singerscontests.services;


import bg.manhattan.singerscontests.exceptions.PasswordNotMatchesException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceProfileDetailsModel;

import javax.mail.MessagingException;
import java.security.Principal;
import java.util.List;
import java.util.Locale;

public interface UserService {
    void register(UserServiceModel model, Locale locale);

    User getCurrentUser(Principal principal) ;

    void deleteUser(String username, String password) ;

    void changeUserPassword(String username, String oldPassword, String newPassword);

    void changeUserProfileDetails(String username, UserServiceProfileDetailsModel userModel);

    UserServiceModel getUserByEmail(String email);

    UserServiceModel getUserByUsername(String userName);

    List<UserServiceModel> getUsersByRole(UserRoleEnum contestManager);

    void changeUserEmail(String username, String newEmail, Locale locale);

    User getUserByRoleAndId(UserRoleEnum contestManager, Long managerId);

    List<UserServiceModel> getUserNotInRole(UserRoleEnum role);

    User getUsersById(Long id);

    void createJuryMember(JuryMemberServiceModel juryModel);

    void editJuryMember(JuryMemberServiceModel juryModel);

    void addUserInRole(Long userId, UserRoleEnum role);

    void removeUserFromRole(Long userId, UserRoleEnum role);

    boolean existsUser(String userName);

    boolean existsByEmail(String email);

    boolean isInRole(String userName, UserRoleEnum role);
}
