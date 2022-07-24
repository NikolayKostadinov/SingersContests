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

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class EditionServiceImpl implements EditionService {
    private final EditionRepository editionRepository;
    private final ContestService contestService;
    private final JuryMemberService juryMemberService;
    private final ModelMapper mapper;

    public EditionServiceImpl(EditionRepository editionRepository,
                              ContestService contestService,
                              JuryMemberService juryMemberService,
                              ModelMapper mapper) {
        this.editionRepository = editionRepository;
        this.contestService = contestService;
        this.juryMemberService = juryMemberService;
        this.mapper = mapper;
    }

    @Override
    public void insert(EditionServiceModel editionModel) {
        Contest contest = contestService.getContestEntityById(editionModel.getContestId());
        Edition edition = this.mapper.map(editionModel, Edition.class).setContest(contest);

        Set<JuryMember> juryMembers = getJuryMembers(editionModel, edition);
        Set<AgeGroup> ageGroups = getAgeGroups(editionModel, edition);
        Set<PerformanceCategory> categories = getCategories(editionModel, edition);

        this.editionRepository.save(edition
                .setJuryMembers(juryMembers)
                .setAgeGroups(ageGroups)
                .setPerformanceCategories(categories));
    }

    @Override
    public EditionServiceModel getById(Long editionId) {
        Edition edition = this.editionRepository.findById(editionId)
                .orElseThrow(() -> new NotFoundException("Edition", editionId));
        return this.mapper.map(edition, EditionServiceModel.class);
    }

    @Override
    public void delete(Long id) {
        this.editionRepository.deleteById(id);
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
                        .setEdition(edition))
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
