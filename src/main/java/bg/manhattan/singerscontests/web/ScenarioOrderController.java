package bg.manhattan.singerscontests.web;


import bg.manhattan.singerscontests.model.pageing.Paged;
import bg.manhattan.singerscontests.model.pageing.Paging;
import bg.manhattan.singerscontests.model.view.EditionListViewModel;
import bg.manhattan.singerscontests.model.view.ScenarioViewModel;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.transaction.Transactional;

@Controller
@RequestMapping("/scenario")
public class ScenarioOrderController extends BaseController {
    private final EditionService editionService;
    private final ContestantService contestantService;
    private final ModelMapper mapper;

    public ScenarioOrderController(EditionService editionService,
                                   ContestantService contestantService,
                                   ModelMapper mapper) {
        this.editionService = editionService;
        this.contestantService = contestantService;
        this.mapper = mapper;
    }

    @GetMapping
    public String getEditions(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                              @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
        setFormTitle("Singers Contests - Editions", model);
        if (!model.containsAttribute("editions")) {
            Paged<EditionListViewModel> viewModel = getEditionListViewModelPaged(pageNumber, size);
            model.addAttribute("editions", viewModel);
        }
        return "scenario-order/editions";
    }

    @GetMapping("/{id}")
    @Transactional
    public String getScenarioOrder(@PathVariable("id") Long id, Model model) {
        setFormTitle("Singers Contests - Scenario order", model);

        ScenarioViewModel scenarioOrder =
                this.contestantService.getContestantsForEditionOrderedByAgeGroupAndScenarioNumber(id);
        model.addAttribute("scenarioOrder", scenarioOrder);
        return "scenario-order/order";
    }

    private Paged<EditionListViewModel> getEditionListViewModelPaged(int pageNumber, int size) {
        Page<EditionListViewModel> futureEditions =
                this.editionService
                        .getEditionsClosedForSubscription(pageNumber, size)
                        .map(esm -> this.mapper.map(esm, EditionListViewModel.class));

        return new Paged<>(futureEditions, Paging.of(futureEditions.getTotalPages(), pageNumber, size));
    }
}
