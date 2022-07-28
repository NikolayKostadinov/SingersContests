package bg.manhattan.singerscontests.services.impl;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.AgeCalculationDto;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.model.view.ErrorViewModel;
import bg.manhattan.singerscontests.services.AgeCalculatorService;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.web.controlleradvice.GlobalExceptionHandler;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Service
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
        EditionServiceModel edition = this.editionService
                .getById(editionId);
        AgeCalculationDto data = this.modelMapper.map(edition, AgeCalculationDto.class)
                .setBirthDate(birthDate);

        int age = this.ageCalculatorService.calculateAge(data);

        return edition.getAgeGroups()
                .stream()
                .filter(ag -> ag.getMinAge() <= age && age <= ag.getMaxAge())
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cannot find age group for age " + age));
    }
}
