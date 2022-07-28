package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.model.view.AgeGroupViewModel;
import bg.manhattan.singerscontests.model.view.ErrorViewModel;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.impl.AgeGroupServiceImpl;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/age-group")
public class AgeGroupRestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(AgeGroupServiceImpl.class);

    private final AgeGroupService ageGroupService;
    private final ModelMapper mapper;

    public AgeGroupRestController(AgeGroupService ageGroupService,
                                  ModelMapper mapper) {
        this.ageGroupService = ageGroupService;
        this.mapper = mapper;
    }

    @GetMapping("{editionId}/{birthDate}")
    public ResponseEntity<AgeGroupViewModel> getAgeGroup(
            @PathVariable("editionId") Long editionId,
            @PathVariable("birthDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate birthDate) {
        AgeGroupServiceModel ageGroup = this.ageGroupService.getAgeGroup(editionId, birthDate);
        return ResponseEntity.ok(this.mapper.map(ageGroup, AgeGroupViewModel.class));
    }

    @ExceptionHandler({NotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorViewModel handleNotFoundException(Exception ex) {
        String details = ex.getMessage();
        ErrorViewModel model =
                new ErrorViewModel("Resource not found", details);
        LOGGER.warn("{} {}", model.getMessage(), ex.getMessage());
        return model;
    }
}
