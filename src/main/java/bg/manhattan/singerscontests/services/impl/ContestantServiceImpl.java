package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.model.service.ContestantServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.model.service.PerformanceCategoryServiceModel;
import bg.manhattan.singerscontests.model.service.SongServiceModel;
import bg.manhattan.singerscontests.repositories.ContestantRepository;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Comparator;

@Service
public class ContestantServiceImpl implements ContestantService {
    private final ContestantRepository repository;
    private final EditionService editionService;

    private final ModelMapper mapper;

    public ContestantServiceImpl(ContestantRepository repository,
                                 EditionService editionService,
                                 ModelMapper mapper) {
        this.repository = repository;
        this.editionService = editionService;
        this.mapper = mapper;
    }

    @Override
    public ContestantServiceModel getContestantModel(Long editionId) {
        EditionServiceModel edition = this.editionService.getById(editionId);
        ContestantServiceModel model = new ContestantServiceModel()
                .setEditionId(edition.getId())
                .setEditionNumber(edition.getNumber())
                .setEditionType(edition.getEditionType())
                .setContestName(edition.getContestName());
        edition.getPerformanceCategories()
                .stream()
                .sorted(Comparator.comparing(PerformanceCategoryServiceModel::getDisplayNumber))
                .forEach(category ->
                        model.getSongs().add(new SongServiceModel()
                                .setCategory(category)));
        return model;
    }
}
