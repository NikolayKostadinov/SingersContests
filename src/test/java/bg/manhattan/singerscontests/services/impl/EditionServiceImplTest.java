package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.entity.PerformanceCategory;
import bg.manhattan.singerscontests.model.enums.AgeCalculationType;
import bg.manhattan.singerscontests.model.enums.EditionType;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.model.service.EditionDetailsServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.model.service.PerformanceCategoryServiceModel;
import bg.manhattan.singerscontests.repositories.ContestantRepository;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.JuryMemberService;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.test_utility.FakeDateTimeProvider;
import bg.manhattan.singerscontests.util.DateTimeProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EditionServiceImplTest {
    private static final LocalDate BEGIN_DATE = LocalDate.of(2022, 9, 23);
    private static final int DAYS_OFFSET = 2;
    private static final LocalDate BEGIN_OF_SUBSCRIPTION_DATE = LocalDate.of(2022, 7, 16);
    private static final int SUBSCRIPTION_DAYS_OFFSET = 30;
    public static final String REGULATIONS = "Some very long regulations";
    public static final String SILVER_YANTRA = "Silver Yantra";
    public static final String PC_1_NAME = "Song in native language";
    public static final String PC_2_NAME = "Song of choice";
    public static final String DELETED_CATEGORY = "Deleted category";
    public static final LocalDate MY_BIRTH_DAY = LocalDate.of(1977, 11, 24);

    @Mock
    private EditionRepository editionRepository;

    @Mock
    private ContestantRepository contestantRepository;

    @Mock
    private ContestService contestService;

    @Mock
    private JuryMemberService juryMemberService;

    @Mock
    private UserService userService;

    @Mock
    private ModelMapper mapper;

    private EditionService editionService;

    @BeforeEach
    public void setUp() {
        this.editionService = new EditionServiceImpl(editionRepository,
                contestantRepository,
                contestService,
                juryMemberService,
                userService,
                mapper);
        DateTimeProvider.setCurrent(new FakeDateTimeProvider(1977, 11, 24, 0, 0));
    }

    @AfterEach
    public void tearDown(){
        DateTimeProvider.ResetToDefault();
    }

    @Test
    void create_with_valid_EditionServiceModel_Creates_Edition() {
        //arrange
        EditionServiceModel model = getModel();
        when(this.contestService.getContestEntityById(1L)).thenReturn(new Contest().setName(SILVER_YANTRA));
        when(this.mapper.map(model, Edition.class)).thenReturn(new Edition());
        when(this.mapper.map(any(PerformanceCategoryServiceModel.class), eq(PerformanceCategory.class)))
                .thenReturn(new PerformanceCategory());
        when(this.mapper.map(any(AgeGroupServiceModel.class), eq(AgeGroup.class)))
                .thenReturn(new AgeGroup());

        // act
        this.editionService.create(model);

        // assert
        verify(this.juryMemberService, times(1))
                .getAllById(argThat(juris ->
                        juris.size() == 3
                                && juris.contains(1L)
                                && juris.contains(2L)
                                && juris.contains(3L)));

        verify(this.editionRepository, times(1))
                .save(any(Edition.class));

    }


    @Test
    void edit_with_valid_EditionServiceModel_Modify_Edition() {
        //arrange
        EditionServiceModel model = getModel();
        model.setId(1L);

        Edition edition = new Edition();
        edition.setId(1L);

        when(this.editionRepository.findById(1L)).thenReturn(Optional.of(edition));
        doNothing().when(this.mapper).map(model, edition);
        when(this.mapper.map(any(PerformanceCategoryServiceModel.class), eq(PerformanceCategory.class)))
                .thenReturn(new PerformanceCategory());
        when(this.mapper.map(any(AgeGroupServiceModel.class), eq(AgeGroup.class)))
                .thenReturn(new AgeGroup());

        // act
        this.editionService.edit(model);

        // assert
        verify(this.juryMemberService, times(1))
                .getAllById(argThat(juris ->
                        juris.size() == 3
                                && juris.contains(1L)
                                && juris.contains(2L)
                                && juris.contains(3L)));

        verify(this.editionRepository, times(1))
                .save(any(Edition.class));
    }

    @Test
    void getById_with_valid_id_will_return_edition() {
        // arrange
        when(this.editionRepository.findById(1L)).thenReturn(Optional.of(new Edition()));
        when(this.mapper.map(any(Edition.class), eq(EditionServiceModel.class))).thenReturn(new EditionServiceModel());

        // act
        this.editionService.getById(1L);

        // assert
        verify(this.editionRepository, times(1)).findById(argThat(ars -> ars.equals(1L)));
    }

    @Test
    void getById_with_invalid_id_will_throw_NotFoundException() {
        // arrange
        when(this.editionRepository.findById(1L)).thenReturn(Optional.empty());

        // assert
        assertThrows(NotFoundException.class,
                () -> this.editionService.getById(1L),
                "Edition id: 1 not found!"
        );
    }

    @Test
    void delete() {
        // act
        this.editionService.delete(1L);

        //assert
        verify(this.editionRepository, times(1))
                .deleteById(argThat(id -> id.equals(1L)));
    }

    @ParameterizedTest
    @CsvSource(value = {"1;2022;2022-01-01;2022-01-31;",
            "2;2020;2020-02-01;2020-02-29;",
            "2;2022;2022-02-01;2022-02-28;",
            "3;2022;2022-03-01;2022-03-31;",
            "4;2022;2022-04-01;2022-04-30;",
            "5;2022;2022-05-01;2022-05-31;",
            "6;2022;2022-06-01;2022-06-30;",
            "7;2022;2022-07-01;2022-07-31;",
            "8;2022;2022-08-01;2022-08-31;",
            "9;2022;2022-09-01;2022-09-30;",
            "10;2022;2022-10-01;2022-10-31;",
            "11;2022;2022-11-01;2022-11-30;",
            "12;2022;2022-12-01;2022-12-31;"}, delimiter = ';')
    void getDatesForMonth_will_invoke_repo_with_valid_dates(int month, int year, String beginStr, String endStr) {
        // arrange
        LocalDate expectedBeginDate = LocalDate.parse(beginStr);
        LocalDate expectedEndDate = LocalDate.parse(endStr);

        // act
        this.editionService.getDatesForMonth(month, year);

        // Assert
        verify(this.editionRepository, times(1)).findAllByBeginDateIsBetween(
                argThat(begin -> begin.equals(expectedBeginDate)),
                argThat(end -> end.equals(expectedEndDate))
        );
    }

    @Test
    void getFutureEditions() {
        //arrange
        when(this.editionRepository
                .findAllAvailableForSubscription(any(LocalDate.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(new Edition())));
        when(this.mapper.map(any(Edition.class), eq(EditionServiceModel.class))).thenReturn(new EditionServiceModel());
        // act
        this.editionService.getEditionsAvailableForRegister(1, 10);

        //assert
        verify(this.editionRepository, times(1))
                .findAllAvailableForSubscription(
                        argThat(date -> date.equals(MY_BIRTH_DAY))
                        , any(PageRequest.class));
    }

    @Test
    void getEditionsByContestInFuture() {
        //arrange
        when(this.editionRepository
                .findAllByContestIdInFuture(any(Long.class), any(LocalDate.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(new Edition())));
        when(this.mapper.map(any(Edition.class), eq(EditionServiceModel.class))).thenReturn(new EditionServiceModel());
        Contest contest = new Contest();
        contest.setId(1L);
        // act
        this.editionService.getEditionsByContestInFuture(contest, 1, 10);

        //assert
        verify(this.editionRepository, times(1))
                .findAllByContestIdInFuture(
                        argThat(id->id.equals(1L)),
                        argThat(date -> date.equals(MY_BIRTH_DAY))
                        , any(PageRequest.class));
    }

    @Test
    void getEditionDetails() {
        // arrange
        Edition edition = new Edition();
        edition.setId(1L);

        when(this.editionRepository.findById(1L)).thenReturn(Optional.of(edition));
        //act
        this.editionService.getEditionDetails(1L);

        //assert
        verify(this.mapper, times(1))
                .map(argThat(ed->((Edition)ed).getId().equals(1L))
                        ,eq(EditionDetailsServiceModel.class));
    }

    @Test
    void getEditionsClosedForSubscription() {
        //arrange
        when(this.editionRepository
                .findAllByEndOfSubscriptionDate(any(LocalDate.class), any(PageRequest.class)))
                .thenReturn(new PageImpl<>(List.of(new Edition())));
        when(this.mapper.map(any(Edition.class), eq(EditionServiceModel.class))).thenReturn(new EditionServiceModel());

        // act
        this.editionService.getEditionsClosedForSubscription(1, 10);

        //assert
        verify(this.editionRepository, times(1))
                .findAllByEndOfSubscriptionDate(
                        argThat(date -> date.equals(MY_BIRTH_DAY))
                        , any(PageRequest.class));
    }

    private EditionServiceModel getModel() {
        return new EditionServiceModel()
                .setNumber(1)
                .setEditionType(EditionType.ATTENDING)
                .setAgeCalculationType(AgeCalculationType.START_OF_CONTEST)
                .setBeginDate(BEGIN_DATE)
                .setEndDate(BEGIN_DATE.plusDays(DAYS_OFFSET))
                .setBeginOfSubscriptionDate(BEGIN_OF_SUBSCRIPTION_DATE)
                .setBeginOfSubscriptionDate(BEGIN_OF_SUBSCRIPTION_DATE.plusDays(SUBSCRIPTION_DAYS_OFFSET))
                .setRegulations(REGULATIONS)
                .setContestId(1L)
                .setContestName(SILVER_YANTRA)
                .setPerformanceCategories(new HashSet<>(getPerformanceCategoryServiceModels()))
                .setAgeGroups(new HashSet<>(getAgeGroupServiceModels()))
                .setJuryMembers(new HashSet<>(Arrays.asList(1L, 2L, 3L)));
    }

    private static List<AgeGroupServiceModel> getAgeGroupServiceModels() {
        return Arrays.asList(
                new AgeGroupServiceModel().setName("I").setMinAge(5).setMaxAge(8).setDisplayNumber(1).setDeleted(false),
                new AgeGroupServiceModel().setName("II").setMinAge(9).setMaxAge(12).setDisplayNumber(2).setDeleted(false),
                new AgeGroupServiceModel().setName("III").setMinAge(13).setMaxAge(16).setDisplayNumber(3).setDeleted(false),
                new AgeGroupServiceModel().setName("IV").setMinAge(17).setMaxAge(25).setDisplayNumber(4).setDeleted(false),
                new AgeGroupServiceModel().setName("Deleted").setMinAge(17).setMaxAge(25).setDisplayNumber(4).setDeleted(true)
        );
    }

    private static List<PerformanceCategoryServiceModel> getPerformanceCategoryServiceModels() {
        return Arrays.asList(
                new PerformanceCategoryServiceModel()
                        .setName(PC_1_NAME)
                        .setDisplayNumber(1)
                        .setRequired(true)
                        .setDeleted(false),
                new PerformanceCategoryServiceModel()
                        .setName(PC_2_NAME)
                        .setDisplayNumber(1)
                        .setRequired(true)
                        .setDeleted(false),
                new PerformanceCategoryServiceModel()
                        .setName(DELETED_CATEGORY)
                        .setDisplayNumber(1)
                        .setRequired(true)
                        .setDeleted(true)
        );
    }
}
