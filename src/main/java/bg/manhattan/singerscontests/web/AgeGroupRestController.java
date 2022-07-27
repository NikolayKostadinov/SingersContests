package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.view.AgeGroupViewModel;
import bg.manhattan.singerscontests.services.AgeGroupService;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/age-group")
public class AgeGroupRestController {
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
        AgeGroup ageGroup = this.ageGroupService.getAgeGroup(editionId, birthDate);
        return ResponseEntity.ok(this.mapper.map(ageGroup, AgeGroupViewModel.class));
    }
}
