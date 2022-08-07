package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.AgeGroupNotFoundException;
import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.model.view.AgeGroupViewModel;
import bg.manhattan.singerscontests.model.view.ErrorViewModel;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.impl.AgeGroupServiceImpl;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Tag(name = "Get the age group of the contestant by Edition ID and Birthday",
            description = "Returns ID and name of the age group determined according to the edition's rules")
    @Parameter(name = "editionId",
            description = "The ID of the Edition",
            required = true)

    @Parameter(name = "birthDate",
            description = "The birthday of the contestant",
            required = true)
    @ApiResponse(responseCode = "200", description = "If the age group was determined successfully")
    @GetMapping("{editionId}/{birthDate}")
    public ResponseEntity<AgeGroupViewModel> getAgeGroup(
            @PathVariable("editionId") Long editionId,
            @PathVariable("birthDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate birthDate) {
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

    @ExceptionHandler({AgeGroupNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody ErrorViewModel handleAgeGroupNotFoundException(Exception ex) {
        String details = ex.getMessage();
        ErrorViewModel model = new ErrorViewModel(ex.getMessage(), "");
        LOGGER.warn("{} {}", model.getMessage(), ex.getStackTrace());
        return model;
    }
}
