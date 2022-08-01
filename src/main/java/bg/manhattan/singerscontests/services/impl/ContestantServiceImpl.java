package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.*;
import bg.manhattan.singerscontests.model.service.ContestantServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.model.service.PerformanceCategoryServiceModel;
import bg.manhattan.singerscontests.model.service.SongServiceModel;
import bg.manhattan.singerscontests.repositories.ContestantRepository;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.UserService;
import jdk.jfr.Category;
import org.hibernate.Hibernate;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Service
public class ContestantServiceImpl implements ContestantService {
    private final ContestantRepository repository;
    private final EditionService editionService;
    private final UserService userService;
    private final AgeGroupService ageGroupService;
    private final ModelMapper mapper;

    public ContestantServiceImpl(ContestantRepository repository,
                                 EditionService editionService,
                                 UserService userService,
                                 AgeGroupService ageGroupService, ModelMapper mapper) {
        this.repository = repository;
        this.editionService = editionService;
        this.userService = userService;
        this.ageGroupService = ageGroupService;
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

    @Override
    public void create(ContestantServiceModel contestantModel, Principal principal) {
        User registrar = this.userService.getCurrentUser(principal);
        Edition edition = this.editionService.getEntityById(contestantModel.getEditionId());
        AgeGroup ageGroup = this.ageGroupService.getAgeGroupEntity(edition, contestantModel.getBirthDay());
        Contestant contestant = this.mapper.map(contestantModel, Contestant.class);
        List<Song> songs = createSongs(contestantModel.getSongs(), edition, contestant);
        //edition.getContestants().add(contestant);
        contestant.setRegistrar(registrar);
        contestant.setEdition(edition);
        contestant.setAgeGroup(ageGroup);
        contestant.setSongs(songs);

        this.repository.save(contestant);
    }

    private List<Song> createSongs(List<SongServiceModel> songs, Edition edition, Contestant contestant) {
        List<Song> list = new ArrayList<>();
        for (SongServiceModel songModel : songs) {
            Song song = this.mapper.map(songModel, Song.class);
            song.setContestant(contestant);
            song.setCategory(edition.getPerformanceCategories()
                    .stream()
                    .filter(c -> c.getId().equals(songModel.getCategory().getId()))
                    .findFirst()
                    .orElseThrow(() ->
                            new NotFoundException(
                                    "PerformanceCategory",
                                    songModel.getCategory().getId())));
            list.add(song);
        }

        return list;
    }
}
