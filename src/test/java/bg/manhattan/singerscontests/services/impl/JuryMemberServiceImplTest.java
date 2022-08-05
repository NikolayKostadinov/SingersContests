package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.JuryMember;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.repositories.JuryMemberRepository;
import bg.manhattan.singerscontests.services.JuryMemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class JuryMemberServiceImplTest {

    @Mock
    public JuryMemberRepository repository;

    @Mock
    public ModelMapper mapper;

    public JuryMemberService juryMemberService;

    @BeforeEach
    public void setUp() {
        this.juryMemberService = new JuryMemberServiceImpl(repository, mapper);
    }

    @Test
    void getAllById_willInvoke_repository_findAllById_withRightParameters() {
        // arrange
        List<Long> ids = Arrays.asList(1L, 2L, 3L);

        // act
        juryMemberService.getAllById(new HashSet<>(ids));

        // assert
        verify(repository, times(1))
                .findAllById(argThat(arg -> {
                    HashSet<Long> result = new HashSet<>();
                    arg.iterator().forEachRemaining(result::add);
                    return result.containsAll(ids);
                }));
    }

    @Test
    void getAll_willInvoke_repository_findAll() {
        // act
        this.juryMemberService.getAll();

        // assert
        verify(repository, times(1)).findAll();
    }

    @Test
    void getJuryMemberById_With_exist_id_returnsCorrectResult() {
        // arrange
        JuryMember juryEntity = new JuryMember();
        when(repository.findById(any(Long.class))).thenReturn(Optional.of(juryEntity));

        when(this.mapper.map(juryEntity, JuryMemberServiceModel.class))
                .thenReturn(new JuryMemberServiceModel().setId(1L));

        // act
        JuryMemberServiceModel jury = this.juryMemberService.getJuryMemberById(1L);

        // assert
        assertEquals(1L, jury.getId().longValue());
    }

    @Test
    void getJuryMemberById_With_not_exist_id_throwsNotFoundException() {
        // arrange
        when(repository.findById(any(Long.class))).thenReturn(Optional.empty());

        // assert
        assertThrows(NotFoundException.class,
                ()->this.juryMemberService.getJuryMemberById(1L),
                "JuryMember id: 1 not found!");
    }
}