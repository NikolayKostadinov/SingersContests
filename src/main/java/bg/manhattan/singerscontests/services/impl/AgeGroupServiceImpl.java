package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.AgeGroupNotFoundException;
import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.services.AgeCalculatorService;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.EditionService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
@Transactional
public class AgeGroupServiceImpl implements AgeGroupService {
    private final EditionService editionService;
    private final AgeCalculatorService ageCalculatorService;
    private final ModelMapper modelMapper;

    public AgeGroupServiceImpl(EditionService editionService, AgeCalculatorService ageCalculatorService, ModelMapper modelMapper) {
        this.editionService = editionService;
        this.ageCalculatorService = ageCalculatorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public AgeGroupServiceModel getAgeGroup(long editionId, LocalDate birthDate) {
        Edition edition = this.editionService.getEntityById(editionId);
        AgeGroup ageGroupEntity = this.getAgeGroupEntity(edition, birthDate);
        return this.modelMapper.map(ageGroupEntity, AgeGroupServiceModel.class);
    }

    @Override
    public AgeGroup getAgeGroupEntity(Edition edition, LocalDate birthDate) {

        int age = getAge(this.modelMapper.map(edition, AgeCalculationDto.class), birthDate);
        Optional<AgeGroup> ageGroup = getAgeGroup(edition, age);

        return ageGroup.orElseThrow(() -> new AgeGroupNotFoundException("Cannot find age group for age " + age));
    }

    @Override
    public boolean isBirthDateInRange(Long editionId, LocalDate birthDate) {
        Edition edition = this.editionService.getEntityById(editionId);
        int age = getAge(this.modelMapper.map(edition, AgeCalculationDto.class), birthDate);

        return this.getAgeGroup(edition, age).isPresent();
    }

    private Optional<AgeGroup> getAgeGroup(Edition edition, int age) {
        return edition.getAgeGroups()
                .stream()
                .filter(ag -> ag.getMinAge() <= age && age <= ag.getMaxAge())
                .findFirst();
    }

    private int getAge(AgeCalculationDto model, LocalDate birthDate) {
        AgeCalculationDto data = model.setBirthDate(birthDate);
        return this.ageCalculatorService.calculateAge(data);
    }
}
