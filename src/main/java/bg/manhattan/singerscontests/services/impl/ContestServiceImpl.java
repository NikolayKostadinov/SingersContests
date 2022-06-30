package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.repositories.ContestRepository;
import bg.manhattan.singerscontests.services.ContestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContestServiceImpl implements ContestService {
    private final ContestRepository repository;
    private final ModelMapper mapper;

    public ContestServiceImpl(ContestRepository repository,
                              ModelMapper mapper) {
        this.repository = repository;
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
}
