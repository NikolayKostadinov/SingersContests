package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.dto.UserLoginDto;
import bg.manhattan.singerscontests.model.dto.UserRegisterDto;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.repositories.UserRepository;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.util.CurrentUser;
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
    ;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    private final HttpSession session;


    public UserServiceImpl(UserRepository userRepository,
                           HttpSession session,
                           PasswordEncoder passwordEncoder,
                           ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
        this.session = session;
    }

    @Override
    public boolean login(UserLoginDto loginDTO) {
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
        if ( currentUser != null){
        LOGGER.debug("User with name [{}] logged out.", this.session.getAttribute("currentUser"));
        this.currentUser.clear();
        }
    }

    @Override
    public void register(UserRegisterDto registerDto) {
//        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())){
//            LOGGER.debug("Fail to create user [{}] passwords not match!",
//                    registerDto.getFirstName() + " " + registerDto.getLastName());
//            return;
//        }
        User user = this.modelMapper.map(registerDto, User.class);
        this.userRepository.save(user);
        LOGGER.debug("Registered user [{}].", registerDto.getFirstName() + " " + registerDto.getLastName());
    }

    private void login(User user) {
        CurrentUser currentUser = new CurrentUser().setName(List.of(user.getFirstName(), user.getMiddleName(), user.getLastName())
                        .stream()
                        .filter(n -> n != null && !n.isEmpty())
                        .collect(Collectors.joining(" ")))
                .setLoggedIn(true);
        this.session.setAttribute("currentUser", currentUser);
        LOGGER.debug("User with name [{}] logged in.", currentUser.getName());
    }


}
