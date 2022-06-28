package bg.manhattan.singerscontests.services;


import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.UserLoginBindingModel;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.UserServiceModel;

import java.util.Optional;

public interface UserService {
    boolean login(UserLoginBindingModel loginDTO);

    void logout();

    void register(UserRegisterBindingModel registerDto);

    boolean isLoggedIn();

    Optional<User> getCurrentUser() throws UserNotFoundException;

    String getCurrentUserName() throws UserNotFoundException;

    Optional<UserServiceModel> getUserByUsername(String userName);

    Optional<UserServiceModel> getUserByEmail(String email);
}
