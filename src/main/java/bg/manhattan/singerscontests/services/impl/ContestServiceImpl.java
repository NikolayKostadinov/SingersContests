package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.ContestCreateServiceModel;
import bg.manhattan.singerscontests.model.service.ContestEditServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Collection;
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
    public List<ContestServiceModel> getAllContests() {
        return this.repository.findAll()
                .stream()
                .map(contest -> this.mapper.map(contest, ContestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ContestServiceModel> getAllContestsByContestManager(Principal principal, boolean isAdmin) throws UserNotFoundException {
        List<Contest> contests;
        if (isAdmin) {
            contests = this.repository.findAll();
        } else {
            User currentUser = this.userService.getCurrentUser(principal);
            contests = this.repository.findAllByManagersContaining(currentUser);
        }
        return contests
                .stream()
                .map(contest -> this.mapper.map(contest, ContestServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void create(ContestCreateServiceModel contestModel) throws UserNotFoundException {
        Contest contest = this.mapper.map(contestModel, Contest.class);

        addManagersToContest(contest, contestModel.getManagers());

        this.repository.save(contest);
    }

    private void addManagersToContest(Contest contest, List<Long> managers) throws UserNotFoundException {
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
    public ContestServiceModel getContestById(Long id) throws NotFoundException {
        Contest contest = getContestEntityById(id);

        return this.mapper.map(contest, ContestServiceModel.class)
                .setManagers(contest
                        .getManagers()
                        .stream()
                        .map(cts -> cts.getId())
                        .toList());
    }

    private Contest getContestEntityById(Long id) throws NotFoundException {
        Contest contest = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contest", id));
        return contest;
    }

    @Override
    public void update(ContestEditServiceModel contestModel) throws NotFoundException, UserNotFoundException {
        Contest contest = getContestEntityById(contestModel.getId());
        this.mapper.map(contestModel, contest);

        contest.getManagers().clear();
        addManagersToContest(contest, contestModel.getManagers());

        this.repository.save(contest);
    }

    @Override
    public ContestServiceModelWithEditions getContestByIdWithEditions(Long id) throws NotFoundException {
        Contest contest = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contest", id));;
        return this.mapper.map(contest, ContestServiceModelWithEditions.class);
    }
}
