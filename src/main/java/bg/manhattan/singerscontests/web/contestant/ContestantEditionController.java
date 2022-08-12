package bg.manhattan.singerscontests.web.contestant;

import bg.manhattan.singerscontests.model.pageing.Paged;
import bg.manhattan.singerscontests.model.pageing.Paging;
import bg.manhattan.singerscontests.model.service.EditionDetailsServiceModel;
import bg.manhattan.singerscontests.model.view.EditionListViewModel;
import bg.manhattan.singerscontests.model.view.EditionDetailsViewModel;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.web.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/contestants/editions")
public class ContestantEditionController extends BaseController {
    private final EditionService editionService;
    private final ModelMapper mapper;

    public ContestantEditionController(EditionService editionService, ModelMapper mapper) {
        this.editionService = editionService;
        this.mapper = mapper;
    }

    @GetMapping
    public String editions(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           @RequestParam(value = "size", required = false, defaultValue = "10") int size, Model model) {
        setFormTitle("Singers Contests - Editions", model);
        if (!model.containsAttribute("editions")) {
            Paged<EditionListViewModel> viewModel = getEditionListViewModelPaged(pageNumber, size);
            model.addAttribute("editions", viewModel);
        }
        return "contestants/editions";
    }

    @GetMapping("/details/{id}")
    public String editionDetails(@PathVariable("id") Long id, Model model) {
        setFormTitle("Singers Contests - Edition details", model);
        EditionDetailsServiceModel editionDetails = this.editionService.getEditionDetails(id);
        EditionDetailsViewModel editionViewModel = this.mapper.map(editionDetails, EditionDetailsViewModel.class);
        model.addAttribute("editionDetails", editionViewModel);
        return "contestants/edition-details";
    }

    private Paged<EditionListViewModel> getEditionListViewModelPaged(int pageNumber, int size) {
        Page<EditionListViewModel> futureEditions =
                this.editionService
                        .getEditionsAvailableForRegister(pageNumber, size)
                        .map(esm -> this.mapper.map(esm, EditionListViewModel.class));

        return new Paged<>(futureEditions, Paging.of(futureEditions.getTotalPages(), pageNumber, size));
    }
}
