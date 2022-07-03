package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.BaseEntity;
import bg.manhattan.singerscontests.model.entity.Contest;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.ContestCreateServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

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
    public void delete(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public void create(ContestCreateServiceModel contestModel) throws UserNotFoundException {
        Contest contest = this.mapper.map(contestModel, Contest.class);
        for (Long managerId : contestModel.getManagers()) {
            contest
                    .getManagers()
                    .add(this.userService
                            .getUserByRoleAndId(UserRoleEnum.CONTEST_MANAGER, managerId));
        }

        this.repository.save(contest);
    }

    @Override
    public ContestServiceModel getContestById(Long id) throws NotFoundException {
        Contest contest = this.repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contest", id));

        return this.mapper.map(contest, ContestServiceModel.class)
                .setManagers(contest
                        .getManagers()
                        .stream()
                        .map(cts -> cts.getId())
                        .toList());
    }
}
