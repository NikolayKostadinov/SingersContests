package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.events.UserChangeEmailEvent;
import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.PasswordNotMatchesException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    public UserRepository repository;

    @Mock
    public ModelMapper mapper;

    @Mock
    public PasswordEncoder passwordEncoder;

    @Mock
    public ApplicationEventPublisher eventPublisher;

    @Mock
    public UserRoleService roleService;

    private UserService userService;

    private static User getUserEntity() {
        return new User(1L, "Pesho",
                "Ivanov",
                "Peshev")
                .setUsername("test")
                .setEmail("pesho@test.com");
    }

    private static UserRole getRole(UserRoleEnum name) {
        UserRole role = new UserRole().setUserRole(name);
        role.setId(1L);
        return role;
    }

    @BeforeEach
    public void setUp() {
        this.userService = new UserServiceImpl(repository,
                mapper,
                passwordEncoder,
                eventPublisher,
                roleService);

    }

    private UserServiceModel getUserModel(User userEntity) {
        return new UserServiceModel()
                .setFirstName(userEntity.getFirstName())
                .setMiddleName(userEntity.getMiddleName())
                .setLastName(userEntity.getLastName())
                .setUsername(userEntity.getUsername())
                .setEmail(userEntity.getEmail());
    }

    @Test
    void changeUserEmail_withExistUser_willChangeEmail() {
        //arrange
        String newEmail = "test@test.com";

        User userEntity = getUserEntity();

        when(this.repository.findByUsername("test"))
                .thenReturn(Optional.of(userEntity));

        when(this.mapper.map(userEntity, UserServiceModel.class))
                .thenReturn(getUserModel(userEntity));

        //act
        this.userService.changeUserEmail("test", newEmail, Locale.getDefault());

        //assert
        verify(this.repository, times(1))
                .save(argThat(user -> user.getUsername().equals("test")
                        && user.getEmail().equals(newEmail)));

        verify(this.eventPublisher, times(1))
                .publishEvent(argThat(ev -> {
                    UserChangeEmailEvent event = (UserChangeEmailEvent) ev;
                    return event.getFullName().equals(userEntity.getFullName())
                            && event.getEmail().equals(newEmail);
                }));
    }

    @Test
    void changeUserEmail_withNotExistUser_willThrowChangeEmail_UsernameNotFoundException() {
        // arrange
        when(this.repository.findByUsername("not_exist"))
                .thenReturn(Optional.empty());

        //assert
        assertThrows(UsernameNotFoundException.class,
                () -> this.userService.changeUserEmail(
                        "not_exist", "test@test.com", Locale.getDefault()),
                "User not_exist not found");
    }

    @Test
    void getUsersById_WithExistId_willReturnUser() {
        //arrange
        when(this.repository.findById(1L))
                .thenReturn(Optional.of(new User()));

        //act
        this.userService.getUsersById(1L);

        //assert
        verify(this.repository, times(1)).findById(1L);
    }

    @Test
    void getUsersById_WithNotExistId_willThrow_NotFoundException() {
        //arrange
        when(this.repository.findById(1L))
                .thenReturn(Optional.empty());

        //assert
        assertThrows(NotFoundException.class,
                () -> this.userService.getUsersById(1L),
                "User id: 1 not found!");
    }

    @Test
    void createJuryMember() {
        //arrange
        User userEntity = getUserEntity();
        JuryMemberServiceModel model = new JuryMemberServiceModel()
                .setId(1L)
                .setUser(getUserModel(userEntity))
                .setDetails("SomeDetails")
                .setImageUrl("ImageUrl");

        JuryMember juryMember = new JuryMember()
                .setId(model.getId())
                .setUser(getUserEntity())
                .setDetails(model.getDetails())
                .setImageUrl(model.getImageUrl());

        when(this.repository.findById(1L))
                .thenReturn(Optional.of(getUserEntity()));

        when(this.mapper.map(model, JuryMember.class))
                .thenReturn(juryMember);

        when(this.roleService.getRoleEntityByName(UserRoleEnum.JURY_MEMBER))
                .thenReturn(getRole(UserRoleEnum.JURY_MEMBER));

        //act
        this.userService.createJuryMember(model);

        //assert
        verify(this.repository, times(1))
                .save(argThat(user ->
                        user.getRoles().size() == 1
                                && user.getRoles()
                                .stream()
                                .anyMatch(r -> r.getUserRole().equals(UserRoleEnum.JURY_MEMBER))
                                && user.getJuryMember().getId().equals(juryMember.getId())
                                && user.getJuryMember().getDetails().equals(juryMember.getDetails())
                                && user.getJuryMember().getImageUrl().equals(juryMember.getImageUrl())
                ));
    }

    @Test
    void editJuryMember() {
        //arrange
        User userEntity = getUserEntity();
        JuryMemberServiceModel model = new JuryMemberServiceModel()
                .setId(1L)
                .setUser(getUserModel(userEntity))
                .setDetails("SomeDetails")
                .setImageUrl("ImageUrl");

        JuryMember oldJuryMember = new JuryMember()
                .setId(1L)
                .setUser(getUserEntity())
                .setDetails("OldDetails")
                .setImageUrl("OldImageUrl");


        JuryMember juryMember = new JuryMember()
                .setId(model.getId())
                .setUser(getUserEntity().setJuryMember(oldJuryMember))
                .setDetails(model.getDetails())
                .setImageUrl(model.getImageUrl());

        when(this.repository.findById(1L))
                .thenReturn(Optional.of(getUserEntity()
                        .setJuryMember(oldJuryMember)
                        .setRoles(new HashSet<>(
                                Collections.singletonList(new UserRole().setUserRole(UserRoleEnum.JURY_MEMBER))))
                ));
        //act
        this.userService.editJuryMember(model);

        //assert
        verify(this.repository, times(1))
                .save(argThat(user ->
                        user.getRoles().size() == 1
                                && user.getRoles()
                                .stream()
                                .anyMatch(r -> r.getUserRole().equals(UserRoleEnum.JURY_MEMBER))
                                && user.getJuryMember().getId().equals(juryMember.getId())
                                && user.getJuryMember().getDetails().equals(juryMember.getDetails())
                                && user.getJuryMember().getImageUrl().equals(juryMember.getImageUrl())
                ));
    }

    @Test
    void addUserInRole_existRoleAndUser_addsUserInRole() {
        //arrange
        when(this.repository.findById(1L))
                .thenReturn(Optional.of(new User()));

        when(this.roleService.getRoleEntityByName(UserRoleEnum.CONTEST_MANAGER))
                .thenReturn(getRole(UserRoleEnum.CONTEST_MANAGER));
        //act
        this.userService.addUserInRole(1L, UserRoleEnum.CONTEST_MANAGER);

        //assert
        verify(this.repository, times(1))
                .save(argThat(user -> user.getRoles()
                        .stream()
                        .anyMatch(r -> r.getUserRole().equals(UserRoleEnum.CONTEST_MANAGER))));
    }

    @Test
    void removeUserFromRole_existRoleAndUser_removesUserInRole() {
        //arrange
        when(this.repository.findById(1L))
                .thenReturn(Optional.of(new User()
                        .setRoles(new HashSet<>(Arrays.asList(
                                new UserRole().setUserRole(UserRoleEnum.ADMIN),
                                new UserRole().setUserRole(UserRoleEnum.JURY_MEMBER),
                                new UserRole().setUserRole(UserRoleEnum.CONTEST_MANAGER)
                        )))));

        when(this.roleService.getRoleEntityByName(UserRoleEnum.CONTEST_MANAGER))
                .thenReturn(getRole(UserRoleEnum.CONTEST_MANAGER));
        //act
        this.userService.removeUserFromRole(1L, UserRoleEnum.CONTEST_MANAGER);

        //assert
        verify(this.repository, times(1))
                .save(argThat(user -> user.getRoles()
                        .stream()
                        .noneMatch(r -> r.getUserRole().equals(UserRoleEnum.CONTEST_MANAGER))));
    }

    @Test
    void existsByEmail() {
        this.userService.existsByEmail("test@test.com");
        verify(this.repository, times(1)).existsByEmail("test@test.com");
    }

    @Test
    void deleteUser_ifPasswordMatches_DeleteUser() {
        //arrange
        when(this.passwordEncoder.matches(any(), any())).thenReturn(true);
        User userEntity = getUserEntity();

        when(this.repository.findByUsername("test"))
                .thenReturn(Optional.of(userEntity));

        //act
        this.userService.deleteUser(userEntity.getUsername(), userEntity.getPassword());

        //assert
        verify(this.repository, times(1)).deleteById(userEntity.getId());
    }

    @Test
    void deleteUser_ifPasswordNotMatches_ThrowsPasswordNotMatchesException() {
        //arrange
        when(this.passwordEncoder.matches(any(), any())).thenReturn(false);
        User userEntity = getUserEntity();

        when(this.repository.findByUsername("test"))
                .thenReturn(Optional.of(userEntity));

        //assert
        assertThrows(PasswordNotMatchesException.class,
                () -> this.userService.deleteUser(userEntity.getUsername(), userEntity.getPassword()));

    }

    @Test
    void changeUserPassword_ifPasswordMatches_ChangeUserPassword() {
        //arrange
        when(this.passwordEncoder.matches(any(), any())).thenReturn(true);
        when(this.passwordEncoder.encode(any())).thenReturn("NewFancyPassw0rd");
        User userEntity = getUserEntity();

        when(this.repository.findByUsername("test"))
                .thenReturn(Optional.of(userEntity));

        //act
        this.userService.changeUserPassword(userEntity.getUsername(), userEntity.getPassword(), "NewFancyPassw0rd");

        //assert
        verify(this.repository, times(1)).save(argThat(user -> user.getPassword().equals("NewFancyPassw0rd")));
    }

    @Test
    void changeUserPassword_ifPasswordMatches_ThrowsPasswordNotMatchesException() {
        //arrange
        when(this.repository.findByUsername("test")).thenReturn(Optional.of(new User()));
        when(this.passwordEncoder.matches(any(), any())).thenReturn(false);

        //assert
        assertThrows(PasswordNotMatchesException.class,
                () -> this.userService
                        .changeUserPassword("test", "WrongPass", "New_Pass"));
    }

    @Test
    void getUserByEmail() {
        // arrange
        when(this.repository.findByEmail("test@test.com"))
                .thenReturn(Optional.of(getUserEntity()));

        // act
        this.userService.getUserByEmail("test@test.com");

        // assert
        verify(this.repository, times(1)).findByEmail("test@test.com");
    }

    @Test
    void getUserByEmail_NonExistEmail_throwsUsernameNotFoundException() {
        // assert
        assertThrows(UsernameNotFoundException.class,
                () -> this.userService.getUserByEmail("test@test.com"),
                "User with test@test.com not found!");
    }


    @Test
    void changeUserProfileDetails() {
        // arrange
        when(this.repository.findByUsername("test")).thenReturn(Optional.of(new User()));

        // act
        this.userService.changeUserProfileDetails("test", new UserServiceProfileDetailsModel());

        // assert
        verify(this.mapper, times(1)).map(any(UserServiceProfileDetailsModel.class), any(User.class));
        verify(this.repository, times(1)).save(any(User.class));
    }
}