package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContestServiceImplTest {

    @Mock
    private ContestRepository repository;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper mapper;

    private ContestService contestService;

    @BeforeEach
    void setUp() {
        this.contestService = new ContestServiceImpl(repository, userService, mapper);
    }

    @Test
    void getAllContests() {
        // assert
        when(this.repository.findAll()).thenReturn(
                Arrays.asList(((Contest) new Contest().setId(1L)).setName("Contest 1"),
                        ((Contest) new Contest().setId(2L)).setName("Contest 2"),
                        ((Contest) new Contest().setId(3L)).setName("Contest 3")));

        // act
        this.contestService.getAllContests();

        // assert
        verify(this.mapper, atLeastOnce())
                .map(argThat(c -> ((Contest) c).getName().equals("Contest 1")), eq(ContestServiceModel.class));

        verify(this.mapper, atLeastOnce())
                .map(argThat(c -> ((Contest) c).getName().equals("Contest 2")), eq(ContestServiceModel.class));

        verify(this.mapper, atLeastOnce())
                .map(argThat(c -> ((Contest) c).getName().equals("Contest 3")), eq(ContestServiceModel.class));
    }

    @Test
    void getAllContestsByContestManager_When_NotAdmin_get_restricted_contestsByManager() {
        // arrange
        when(this.repository.findAllByManagersContaining(any(User.class), any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(new Contest())));
        when(this.userService.getCurrentUser(any())).thenReturn(new User());

        // act
        this.contestService
                .getAllContestsByContestManager(() -> "Manager 1", false, 1, 10);

        // assert
        verify(repository, times(1))
                .findAllByManagersContaining(any(User.class), any(PageRequest.class));
    }

    @Test
    void getAllContestsByContestManager_When_NotAdmin_get_All_contestsByManager() {
        // arrange
        when(this.repository.findAll(any(PageRequest.class))).thenReturn(new PageImpl<>(List.of(new Contest())));

        // act
        this.contestService
                .getAllContestsByContestManager(() -> "Manager 1", true, 1, 10);

        // assert
        verify(repository, times(1)).findAll(any(PageRequest.class));
    }

    @Test
    void delete() {
    }

    @Test
    void create() {
    }

    @Test
    void getContestById() {
    }

    @Test
    void getContestEntityById() {
    }

    @Test
    void isOwner() {
    }

    @Test
    void update() {
    }

    @Test
    void getContestByIdWithEditions() {
    }
}
