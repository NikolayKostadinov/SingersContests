package bg.manhattan.singerscontests.services;

import bg.manhattan.singerscontests.model.dto.AgeCalculationDto;
import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.repositories.EditionRepository;
import bg.manhattan.singerscontests.services.age_calculation.AgeCalculatorService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AgeGroupServiceImpl implements AgeGroupService {
    private final EditionRepository editionRepository;
    private final AgeCalculatorService ageCalculatorService;
    private final ModelMapper modelMapper;

    public AgeGroupServiceImpl(EditionRepository editionRepository,
                               AgeCalculatorService ageCalculatorService,
                               ModelMapper modelMapper) {
        this.editionRepository = editionRepository;
        this.ageCalculatorService = ageCalculatorService;
        this.modelMapper = modelMapper;
    }

    @Override
    public AgeGroup getAgeGroup(long editionId, LocalDate birthDate) {
        Edition edition = this.editionRepository
                .findById(editionId)
                .orElseThrow(() -> new IllegalArgumentException("Cannot find edition Id: " + editionId));
        AgeCalculationDto data = this.modelMapper.map(edition, AgeCalculationDto.class);
        data.setBirthDate(birthDate);

        int age = this.ageCalculatorService.calculateAge(data);

        return edition.getAgeGroups()
                .stream()
                .filter(ag -> ag.getMinAge() <= age && age <= ag.getMaxAge())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find age group for age " + age));
    }
}