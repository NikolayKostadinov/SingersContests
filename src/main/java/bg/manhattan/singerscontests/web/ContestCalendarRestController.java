package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.view.DatesViewModel;
import bg.manhattan.singerscontests.services.EditionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@RestController
@RequestMapping("/api/editions")
public class ContestCalendarRestController {
    private final EditionService editionService;

    public ContestCalendarRestController(EditionService editionService) {
        this.editionService = editionService;
    }

    @GetMapping("/{month}/{year}")
    public ResponseEntity<DatesViewModel> getDatesInMonth(@PathVariable("month") int month,
                                                          @PathVariable("year") int year){
        DatesViewModel model = new DatesViewModel(this.editionService.getDatesForMonth(month, year));
        return ResponseEntity.ok(model);
    }
}
