package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.repositories.UserRepository;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
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
