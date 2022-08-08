package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.*;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContestServiceImpl implements ContestService {
    private final ContestRepository repository;
    private final UserService userService;
    private final ModelMapper mapper;

    public ContestServiceImpl(ContestRepository repository,
                              UserService userService, ModelMapper mapper) {
        this.repository = repository;
        this.userService = userService;
        this.mapper = mapper;
    }

    @Override
    @Cacheable("contests")
    public List<ContestServiceModel> getAllContests() {
        return this.repository.findAll()
                .stream()
                .map(contest -> this.mapper.map(contest, ContestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    @Cacheable("contests")
    public Page<ContestServiceModel> getAllContestsByContestManager(Principal principal, boolean isAdmin, int pageNumber, int size) {
        Page<Contest> contests;
        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        PageRequest request = PageRequest.of(pageNumber - 1, size, sort);

        if (isAdmin) {
            contests = this.repository.findAll(request);
        } else {
            User currentUser = this.userService.getCurrentUser(principal);
            contests = this.repository.findAllByManagersContaining(currentUser, request);
        }

        return contests
                .map(contest -> this.mapper.map(contest, ContestServiceModel.class));
    }

    @Override
    @CacheEvict(cacheNames = "contests", allEntries = true)
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    @CacheEvict(cacheNames = "contests", allEntries = true)
    public void create(ContestCreateServiceModel contestModel) {
        Contest contest = this.mapper.map(contestModel, Contest.class);

        addManagersToContest(contest, contestModel.getManagers());

        this.repository.save(contest);
    }

    private void addManagersToContest(Contest contest, List<Long> managers) {
        for (Long managerId : managers) {
            if (managerId != null) {
                contest
                        .getManagers()
                        .add(this.userService
                                .getUserByRoleAndId(UserRoleEnum.CONTEST_MANAGER, managerId));
            }
        }
    }

    @Override
    @Cacheable("contests")
    public ContestServiceModel getContestById(Long id) {
        Contest contest = getContestEntityById(id);

        return this.mapper.map(contest, ContestServiceModel.class)
                .setManagers(contest
                        .getManagers()
                        .stream()
                        .map(cts -> cts.getId())
                        .toList());
    }

    @Override
    public Contest getContestEntityById(Long id) {
        Contest contest = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contest", id));
        return contest;
    }

    @Override
    public boolean isOwner(String userName, Long id) {
        UserServiceModel currentUser = this.userService.getUserByUsername(userName);
        if (isAdmin(currentUser)) {
            return true;
        }

        Contest contest = getContestEntityById(id);

        return contest.getManagers()
                .stream()
                .anyMatch(manager -> manager.getId().equals(currentUser.getId()));
    }

    private boolean isAdmin(UserServiceModel currentUser) {
        return currentUser.getRoles()
                .stream()
                .anyMatch(role -> role == UserRoleEnum.ADMIN.name());
    }

    @Override
    @CacheEvict(cacheNames = "contests", allEntries = true)
    public void update(ContestEditServiceModel contestModel) {
        Contest contest = getContestEntityById(contestModel.getId());
        this.mapper.map(contestModel, contest);

        contest.getManagers().clear();
        addManagersToContest(contest, contestModel.getManagers());

        this.repository.save(contest);
    }

    @Override
    public ContestServiceModelWithEditions getContestByIdWithEditions(Long id) {
        Contest contest = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contest", id));
        return this.mapper.map(contest, ContestServiceModelWithEditions.class);
    }
}
