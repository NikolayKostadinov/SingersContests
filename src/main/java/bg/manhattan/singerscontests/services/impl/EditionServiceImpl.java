package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.entity.*;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.JuryMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EditionServiceImpl implements EditionService {
    private final EditionRepository repository;
    private final ContestService contestService;
    private final JuryMemberService juryMemberService;
    private final ModelMapper mapper;

    public EditionServiceImpl(EditionRepository repository,
                              ContestService contestService,
                              JuryMemberService juryMemberService,
                              ModelMapper mapper) {
        this.repository = repository;
        this.contestService = contestService;
        this.juryMemberService = juryMemberService;
        this.mapper = mapper;
    }

    @Override
    public void create(EditionServiceModel editionModel) throws NotFoundException {
        Contest contest = contestService.getContestEntityById(editionModel.getContestId());
        Edition edition = this.mapper.map(editionModel, Edition.class).setContest(contest);

        Set<JuryMember> juryMembers = getJuryMembers(editionModel, edition);
        List<AgeGroup> ageGroups = getAgeGroups(editionModel, edition);
        List<PerformanceCategory> categories = getCategories(editionModel, edition);

        this.repository.save(edition
                .setJuryMembers(juryMembers)
                .setAgeGroups(new LinkedHashSet<>(ageGroups))
                .setPerformanceCategories(new LinkedHashSet<>(categories)));
    }

    @Override
    public EditionServiceModel getById(Long editionId) throws NotFoundException {
        Edition edition = this.repository.findById(editionId)
                .orElseThrow(() -> new NotFoundException("Edition", editionId));
        return this.mapper.map(edition, EditionServiceModel.class);
    }

    private Set<JuryMember> getJuryMembers(EditionServiceModel editionModel, Edition edition) {
        return this.juryMemberService
                .getAllById(editionModel.getJuryMembers())
                .stream()
                .map(member -> {
                    member.getEditions().add(edition);
                    return member;
                })
                .collect(Collectors.toSet());
    }

    private List<PerformanceCategory> getCategories(EditionServiceModel editionModel, Edition edition) {
        return editionModel.getPerformanceCategories()
                .stream()
                .map(category -> this.mapper.map(category, PerformanceCategory.class)
                        .setEdition(edition))
                .toList();
    }

    private List<AgeGroup> getAgeGroups(EditionServiceModel editionModel, Edition edition) {
        return editionModel.getAgeGroups()
                .stream()
                .map(ageGroup -> this.mapper.map(ageGroup, AgeGroup.class)
                        .setEdition(edition))
                .toList();
    }
}
