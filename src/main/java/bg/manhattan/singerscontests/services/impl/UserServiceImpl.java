package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.events.UserChangeEmailEvent;
import bg.manhattan.singerscontests.events.UserRegisteredEvent;
import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.PasswordNotMatchesException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.JuryMember;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.entity.UserRole;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceProfileDetailsModel;
import bg.manhattan.singerscontests.repositories.UserRepository;
import bg.manhattan.singerscontests.services.UserRoleService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository repository;
    private final ModelMapper mapper;
    private final PasswordEncoder passwordEncoder;

    private final ApplicationEventPublisher eventPublisher;
    private final UserRoleService roleService;

    public UserServiceImpl(UserRepository userRepository,
                           ModelMapper mapper,
                           PasswordEncoder passwordEncoder,
                           ApplicationEventPublisher eventPublisher,
                           UserRoleService roleService) {
        this.repository = userRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
        this.eventPublisher = eventPublisher;
        this.roleService = roleService;
    }


    @Override
    public void register(UserServiceModel model, Locale locale){
        User user = this.mapper.map(model, User.class);
        this.repository.save(user);
        this.eventPublisher.publishEvent(
                new UserRegisteredEvent(this, model.getEmail(), model.getFullName(), locale));
    }

    @Override
    public User getCurrentUser(Principal principal) {
        return this.repository.findByUsername(principal.getName())
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserServiceModel getUserByUsername(String userName) {
        User user = getUserEntityByUserName(userName);
        return toUserServiceModel(user);
    }

    private User getUserEntityByUserName(String userName) {
        return this.repository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException("User " + userName + "not found!"));
    }

    @Override
    public List<UserServiceModel> getUsersByRole(UserRoleEnum contestManager) {
        return this.repository.findByRole(contestManager)
                .stream()
                .map(user -> mapper.map(user, UserServiceModel.class))
                .toList();
    }

    @Override
    public void changeUserEmail(String username, String newEmail, Locale locale) {
        User userEntity = getUserEntityByUserName(username);
        UserServiceModel userModel = this.mapper.map(userEntity, UserServiceModel.class);
        userEntity.setEmail(newEmail);
        this.repository.save(userEntity);
        this.eventPublisher.publishEvent(new UserChangeEmailEvent(this, newEmail, userModel.getFullName(), locale));
    }

    @Override
    public User getUserByRoleAndId(UserRoleEnum role, Long managerId) {

        List<User> usersByRoleAndId = this.repository.findByRoleAndId(role, managerId);
        if (usersByRoleAndId.isEmpty()) throw new UserNotFoundException(managerId.toString());
        return usersByRoleAndId.get(0);
    }

    @Override
    public List<UserServiceModel> getUserNotInRole(UserRoleEnum role) {
        return this.repository.findNotInRole(role)
                .stream()
                .map(user -> mapper.map(user, UserServiceModel.class))
                .toList();
    }

    @Override
    public User getUsersById(Long id) {
        return this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User", id));
    }

    @Override
    public void createJuryMember(JuryMemberServiceModel juryModel) {
        User user = this.getUsersById(juryModel.getId());
        JuryMember juryMember = this.mapper.map(juryModel, JuryMember.class);
        juryMember.setUser(user);
        user.setJuryMember(juryMember);
        this.addUserInRole(user, UserRoleEnum.JURY_MEMBER);
        this.repository.save(user);
    }

    @Override
    public void editJuryMember(JuryMemberServiceModel juryModel) {
        User user = this.getUsersById(juryModel.getId());
        JuryMember juryMember = user.getJuryMember();
        juryMember.setDetails(juryModel.getDetails());
        juryMember.setImageUrl(juryModel.getImageUrl());
        user.setJuryMember(juryMember);
        this.repository.save(user);
    }

    @Override
    public void addUserInRole(Long userId, UserRoleEnum role) {
        User user = this.getUsersById(userId);
        addUserInRole(user, role);
        this.repository.save(user);
    }

    private void addUserInRole(User user, UserRoleEnum role) {
        if (user.getRoles().stream().noneMatch(r -> r.getUserRole() == role)) {
            UserRole roleEntity = this.roleService.getRoleEntityByName(role);
            user.getRoles().add(roleEntity);
        }
    }

    @Override
    public void removeUserFromRole(Long userId, UserRoleEnum role) {
        User user = this.getUsersById(userId);
        if (user.getRoles().stream().anyMatch(r -> r.getUserRole() == role)) {
            UserRole roleEntity = this.roleService.getRoleEntityByName(role);
            user.getRoles().remove(roleEntity);
            this.repository.save(user);
        }
    }

    @Override
    public boolean existsUser(String userName) {
        return this.repository.existsByUsername(userName);
    }

    @Override
    public boolean existsByEmail(String email) {
        return this.repository.existsByEmail(email);
    }

    @Override
    public boolean isInRole(String userName, UserRoleEnum role) {
        UserServiceModel userByUsername = this.getUserByUsername(userName);
        return userByUsername.isInRole(role);
    }

    @Override
    public void deleteUser(String username, String password) {
        User user = getUserEntityByUserName(username);
        if (passwordNotMatches(password, user.getPassword())) {
            throw new PasswordNotMatchesException();
        }

        this.repository.deleteById(user.getId());
    }

    @Override
    public void changeUserPassword(String username, String oldPassword, String newPassword){
        User user = getUserEntityByUserName(username);
        if (passwordNotMatches(oldPassword, user.getPassword())) {
            throw new PasswordNotMatchesException();
        }

        user.setPassword(this.passwordEncoder.encode(newPassword));
        this.repository.save(user);
    }


    private boolean passwordNotMatches(String password, String encodedPassword) {
        return !this.passwordEncoder
                .matches(password, encodedPassword);
    }

    @Override
    public void changeUserProfileDetails(String username,
                                         UserServiceProfileDetailsModel userModel) throws UserNotFoundException {
        User userEntity = getUserEntityByUserName(username);
        this.mapper.map(userModel, userEntity);
        this.repository.save(userEntity);
    }

    @Override
    public UserServiceModel getUserByEmail(String email) {
        User user = this.repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with " + email + " not found!"));
        return toUserServiceModel(user);
    }

    private UserServiceModel toUserServiceModel(User user) {
        UserServiceModel userModel = this.mapper.map(user, UserServiceModel.class);
        user.getRoles()
                .forEach(role -> userModel.addRole(role.getUserRole().name()));
        return userModel;
    }
}
