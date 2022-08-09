package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.*;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import bg.manhattan.singerscontests.model.service.EditionDetailsServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.repositories.ContestantRepository;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.JuryMemberService;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.util.DateTimeProvider;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;

@Service
@Transactional()
public class EditionServiceImpl implements EditionService {
    private final EditionRepository editionRepository;
    private final ContestantRepository contestantRepository;
    private final ContestService contestService;
    private final JuryMemberService juryMemberService;
    private final UserService userService;
    private final ModelMapper mapper;

    public EditionServiceImpl(EditionRepository editionRepository,
                              ContestantRepository contestantRepository,
                              ContestService contestService,
                              JuryMemberService juryMemberService,
                              UserService userService,
                              ModelMapper mapper) {
        this.editionRepository = editionRepository;
        this.contestantRepository = contestantRepository;
        this.contestService = contestService;
        this.juryMemberService = juryMemberService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    public void create(EditionServiceModel editionModel) {
        Contest contest = contestService.getContestEntityById(editionModel.getContestId());
        Edition edition = this.mapper.map(editionModel, Edition.class).setContest(contest);
        mapCollectionProperties(editionModel, edition);
        this.editionRepository.save(edition);
    }

    @Override
    public void edit(EditionServiceModel editionModel) {
        Edition edition = this.getEntityById(editionModel.getId());
        this.mapper.map(editionModel, edition);
        mapCollectionProperties(editionModel, edition);
        this.editionRepository.save(edition);
    }

    private void mapCollectionProperties(EditionServiceModel editionModel, Edition edition) {
        Set<JuryMember> juryMembers = getJuryMembers(editionModel, edition);
        Set<AgeGroup> ageGroups = getAgeGroups(editionModel, edition);
        Set<PerformanceCategory> categories = getCategories(editionModel, edition);

        edition.getJuryMembers().clear();
        edition.getJuryMembers().addAll(juryMembers);

        edition.getAgeGroups().clear();
        edition.getAgeGroups().addAll(ageGroups);

        edition.getPerformanceCategories().clear();
        edition.getPerformanceCategories().addAll(categories);
    }

    @Override
    public EditionServiceModel getById(Long editionId) {
        Edition edition = this.getEntityById(editionId);
        return this.mapper.map(edition, EditionServiceModel.class);
    }

    @Override
    public Edition getEntityById(Long editionId) {
        return this.editionRepository.findById(editionId)
                .orElseThrow(() -> new NotFoundException("Edition", editionId));
    }

    @Override
    public void delete(Long id) {
        this.editionRepository.deleteById(id);
    }

    @Override
    public List<LocalDate> getDatesForMonth(int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.getMonth().length(start.isLeapYear()));
        return this.editionRepository.findAllByBeginDateIsBetween(start, end);
    }

    @Override
    public Page<EditionServiceModel> getFutureEditions(int pageNumber, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "beginDate");
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort);
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();

        return this.editionRepository
                .findAllAvailableForSubscription(today, request)
                .map(e -> this.mapper.map(e, EditionServiceModel.class));
    }

    @Override
    public Page<EditionServiceModel> getEditionsByContestInFuture(Contest contest, int pageNumber, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "beginDate");
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort);
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();

        return this.editionRepository
                .findAllByContestIdInFuture(contest.getId(), today, request)
                .map(e -> this.mapper.map(e, EditionServiceModel.class));
    }

    @Override
    public EditionDetailsServiceModel getEditionDetails(Long editionId) {
        Edition edition = this.editionRepository
                .findById(editionId)
                .orElseThrow(() -> new NotFoundException("Edition", editionId));


        return this.mapper.map(edition, EditionDetailsServiceModel.class);
    }

    @Override
    public void generateScenarioOrder(LocalDate targetDate) {
        List<Edition> editions = this.editionRepository.findAllByEndOfSubscriptionDate(targetDate);
        editions
                .stream()
                .forEach(edition -> {
                    Map<AgeGroup, List<Contestant>> ageGroups = edition.getContestants()
                            .stream()
                            .collect(groupingBy(Contestant::getAgeGroup));
                    ageGroups.entrySet()
                            .stream()
                            .forEach(ag -> {
                                List<Contestant> contestants = ag.getValue();
                                Collections.shuffle(contestants);
                                AtomicInteger ix = new AtomicInteger(1);
                                contestants.forEach(c -> c.setScenarioNumber(ix.getAndIncrement()));
                            });

                    this.contestantRepository.saveAll(ageGroups.values()
                            .stream()
                            .flatMap(Collection::stream)
                            .toList());
                });

        this.editionRepository.saveAll(editions);
    }

    @Override
    public ContestServiceModelWithEditions getEditionsInFutureByContestId(Long contestId, int pageNumber, int size) {
        Contest contest = this.contestService.getContestEntityById(contestId);
        Page<EditionServiceModel> editions = this.getEditionsByContestInFuture(contest, pageNumber, size);
        return new ContestServiceModelWithEditions(contest.getId(), contest.getName(), editions);
    }

    @Override
    public boolean isEditionOwner(String userName, Long id) {
        Edition edition = getEntityById(id);
        return this.contestService.isOwner(userName, edition.getContest().getId());
    }

    @Override
    public boolean isJuryDutyAvailable(String userName, Long id) {
        UserServiceModel user = this.userService.getUserByUsername(userName);
        Edition edition = getEntityById(id);
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();
        return user.isInRole(UserRoleEnum.JURY_MEMBER)
                && edition.getBeginDate().compareTo(today) <= 0
                && edition.getEndDate().compareTo(today) >= 0
                && edition.getJuryMembers()
                .stream()
                .anyMatch(jm -> jm.getId().equals(user.getId()));
    }

    @Override
    public Page<EditionServiceModel> getEditionsClosedForSubscription(int pageNumber, int size) {
        Sort sort = Sort.by(Sort.Direction.ASC, "beginDate");
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort);
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();

        return this.editionRepository
                .findAllByEndOfSubscriptionDate(today, request)
                .map(e -> this.mapper.map(e, EditionServiceModel.class));
    }

    @Override
    public Page<EditionServiceModel> getEditionsActiveForJuryMember(Principal principal, int pageNumber, int size) {
        User currentUser = this.userService.getCurrentUser(principal);
        Sort sort = Sort.by(Sort.Direction.ASC, "beginDate");
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort);
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();

        return this.editionRepository.findAllActiveForJuryMember(today, currentUser.getId(), request)
                .map(e -> this.mapper.map(e, EditionServiceModel.class));
    }

    @Override
    public Page<EditionServiceModel> getFinishedEditions(int pageNumber, int size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "endDate");
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort);
        LocalDate today = DateTimeProvider.getCurrent().utcNow().toLocalDate();

        return this.editionRepository.findAllFinishedEditions(today, request)
                .map(e -> this.mapper.map(e, EditionServiceModel.class));
    }


    private Set<JuryMember> getJuryMembers(EditionServiceModel editionModel, Edition edition) {
        return this.juryMemberService
                .getAllById(editionModel.getJuryMembers())
                .stream()
                .peek(member -> member.getEditions().add(edition))
                .collect(Collectors.toSet());
    }

    private Set<PerformanceCategory> getCategories(EditionServiceModel editionModel, Edition edition) {
        return editionModel.getPerformanceCategories()
                .stream()
                .filter(category -> !category.isDeleted())
                .map(category -> this.mapper.map(category, PerformanceCategory.class)
                        .setEdition(edition)
                )
                .collect(Collectors.toSet());
    }

    private Set<AgeGroup> getAgeGroups(EditionServiceModel editionModel, Edition edition) {
        return editionModel.getAgeGroups()
                .stream()
                .filter(ageGroup -> !ageGroup.isDeleted())
                .map(ageGroup -> this.mapper.map(ageGroup, AgeGroup.class)
                        .setEdition(edition))
                .collect(Collectors.toSet());
    }
}
