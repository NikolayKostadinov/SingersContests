package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.PasswordNotMatchesException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceProfileDetailsModel;
import bg.manhattan.singerscontests.repositories.UserRepository;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public void register(UserRegisterBindingModel registerDto) {
        User user = this.mapper.map(registerDto, User.class);
        this.userRepository.save(user);
    }

    @Override
    public User getCurrentUser(Principal principal) throws UserNotFoundException {
        return this.userRepository.findByUsername(principal.getName())
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public Optional<UserServiceModel> getUserByUsername(String userName) {
        Optional<User> user = this.userRepository.findByUsername(userName);
        return toUserServiceModel(user);
    }

    @Override
    public List<UserServiceModel> getUsersByRole(UserRoleEnum contestManager) {
        return this.userRepository.findByRole(contestManager)
                .stream()
                .map(user -> mapper.map(user, UserServiceModel.class))
                .toList();
    }

    @Override
    public void changeUserEmail(String username, String newEmail) throws UserNotFoundException {
        User userEntity = getUser(username);
        userEntity.setEmail(newEmail);
        this.userRepository.save(userEntity);
    }

    @Override
    public void deleteUser(String username, String password) throws UserNotFoundException, PasswordNotMatchesException {
        User user = getUser(username);
        if (!passwordMatches(password, user.getPassword())){
            throw new PasswordNotMatchesException();
        }

        this.userRepository.deleteById(user.getId());
    }

    @Override
    public void changeUserPassword(String username, String oldPassword, String newPassword) throws UserNotFoundException, PasswordNotMatchesException {
        User user = getUser(username);
        if (!passwordMatches(oldPassword, user.getPassword())){
            throw new PasswordNotMatchesException();
        }

        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.userRepository.save(user);
    }


    private boolean passwordMatches(String password, String encodedPassword) {
        return this.passwordEncoder.matches(password, encodedPassword);
    }

    @Override
    public void changeUserProfileDetails(String username, UserServiceProfileDetailsModel userModel) throws UserNotFoundException {
        User userEntity = getUser(username);
        this.mapper.map(userModel, userEntity);
        this.userRepository.save(userEntity);
    }

    private User getUser(String username) throws UserNotFoundException {
        User userEntity = this.userRepository.findByUsername(username)
                .orElseThrow(()-> new UserNotFoundException(username));
        return userEntity;
    }

    @Override
    public Optional<UserServiceModel> getUserByEmail(String email) {
        Optional<User> user = this.userRepository.findByEmail(email);
        return toUserServiceModel(user);
    }

    private Optional<UserServiceModel> toUserServiceModel(Optional<User> user) {
        return user.map(value -> {
            UserServiceModel userModel = this.mapper.map(value, UserServiceModel.class);
            value.getRoles()
                    .forEach(role -> userModel.addRole(role.getUserRole().name()));
            return userModel;
        });
    }
}
