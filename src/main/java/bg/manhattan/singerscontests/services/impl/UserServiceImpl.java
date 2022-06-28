package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.IHaveNames;
import bg.manhattan.singerscontests.model.binding.UserLoginBindingModel;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.repositories.UserRepository;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.util.CurrentUser;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper mapper;

    private final HttpSession session;


    public UserServiceImpl(UserRepository userRepository,
                           HttpSession session,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mapper = modelMapper;
        this.session = session;
    }

    @Override
    public boolean login(UserLoginBindingModel loginDTO) {
        Optional<User> userOpt = this.userRepository.findByUsername(loginDTO.getUsername());
        if (userOpt.isEmpty()) {
            LOGGER.debug("User with name [{}] not found.", loginDTO.getUsername());
            return false;
        }

        boolean success = this.passwordEncoder.matches(loginDTO.getPassword(), userOpt.get().getPassword());
        if (success) {
            login(userOpt.get());
        } else {
            logout();
        }

        return success;
    }

    @Override
    public void logout() {
        CurrentUser currentUser = (CurrentUser) this.session.getAttribute("currentUser");
        if (currentUser != null) {
            LOGGER.debug("User with name [{}] logged out.", currentUser.getName());
            this.session.removeAttribute("currentUser");
        }
    }

    @Override
    public void register(UserRegisterBindingModel registerDto) {
//        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
//            LOGGER.debug("Fail to create user [{}] passwords not match!",
//                    registerDto.getFirstName() + " " + registerDto.getLastName());
//            return;
//        }
        User user = this.mapper.map(registerDto, User.class);
        this.userRepository.save(user);
        LOGGER.debug("Registered user [{}].", getFullName(registerDto));
    }

    @Override
    public boolean isLoggedIn() {
        CurrentUser currentUser = (CurrentUser) this.session.getAttribute("currentUser");
        return currentUser != null;
    }

    @Override
    public Optional<User> getCurrentUser() throws UserNotFoundException {
        CurrentUser currentUser = getCurrentUserFromSession();
        return this.userRepository.findByUsername(currentUser.getName());
    }

    @Override
    public String getCurrentUserName() throws UserNotFoundException {
        return getCurrentUserFromSession().getName();
    }

    private CurrentUser getCurrentUserFromSession() throws UserNotFoundException {
        CurrentUser currentUser = (CurrentUser) this.session.getAttribute("currentUser");
        if (currentUser == null) {
            throw new UserNotFoundException();
        }
        return currentUser;
    }

    @Override
    public Optional<UserServiceModel> getUserByUsername(String userName) {
        Optional<User> userEntity = this.userRepository.findByUsername(userName);
        return userEntity.map(user -> this.mapper.map(user, UserServiceModel.class));
    }

    @Override
    public Optional<UserServiceModel> getUserByEmail(String email) {
        Optional<User> userEntity = this.userRepository.findByEmail(email);
        return userEntity.map(user -> this.mapper.map(user, UserServiceModel.class));
    }

    private void login(User user) {
        CurrentUser currentUser = getFullName(user)
                .setLoggedIn(true);
        this.session.setAttribute("currentUser", currentUser);
        LOGGER.debug("User with name [{}] logged in.", currentUser.getName());
    }

    private CurrentUser getFullName(IHaveNames user) {
        return new CurrentUser().setName(List.of(user.getFirstName(), user.getMiddleName(), user.getLastName())
                .stream()
                .filter(n -> n != null && !n.isEmpty())
                .collect(Collectors.joining(" ")));
    }


}
