package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.binding.ScoreBindingModel;
import bg.manhattan.singerscontests.model.pageing.Paged;
import bg.manhattan.singerscontests.model.pageing.Paging;
import bg.manhattan.singerscontests.model.service.ScoreServiceModel;
import bg.manhattan.singerscontests.model.view.EditionListViewModel;
import bg.manhattan.singerscontests.model.view.ScenarioViewModel;
import bg.manhattan.singerscontests.model.view.ScoreCardViewModel;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.SongService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/jury")
public class JuryController extends BaseController{
    private final EditionService editionService;
    private final ContestantService contestantService;
    private final SongService songService;
    private final ModelMapper mapper;

    public JuryController(EditionService editionService,
                          ContestantService contestantService, SongService songService,
                          ModelMapper mapper) {
        this.editionService = editionService;
        this.contestantService = contestantService;
        this.songService = songService;
        this.mapper = mapper;
    }

    @GetMapping("/editions")
    @Transactional
    public String getEditionsForJuryMember(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                           Model model,
                                           Principal principal) {
        setFormTitle("Singers Contests - Editions", model);
        if (!model.containsAttribute("editions")) {
            Paged<EditionListViewModel> viewModel = getEditionListViewModelPaged(principal, pageNumber, size);
            model.addAttribute("editions", viewModel);
        }
        return "jury/editions";
    }

    @GetMapping("/scenario/{id}")
    @Transactional
    public String getScenarioOrder(@PathVariable("id") Long id, Model model) {
        setFormTitle("Singers Contests - Scenario order", model);

        ScenarioViewModel scenarioOrder =
                this.contestantService.getContestantsForEditionOrderedByAgeGroupAndScenarioNumber(id);
        model.addAttribute("scenarioOrder", scenarioOrder);
        return "jury/order";
    }

    @GetMapping("/scorecard/{songId}")
    @Transactional
    public String getScoreCard(@PathVariable("songId") Long songId, Model model, Principal principal) {
        setFormTitle("Singers Contests - Score Card", model);

        ScoreCardViewModel scoreCardViewModel = getScoreCardModel(songId, principal);
        model.addAttribute("scoreCard", scoreCardViewModel);
        return "jury/scorecard";
    }

    @PostMapping("/scorecard/{songId}")
    public String insertScore(@Valid ScoreBindingModel model,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Principal principal) {
        if (bindingResult.hasErrors()) {
            ScoreCardViewModel scoreCardViewModel = getScoreCardModel(model.getSongId(), principal);
            this.mapper.map(model, scoreCardViewModel);
            redirectAttributes.addFlashAttribute("scoreCard", scoreCardViewModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.scoreCard", bindingResult);

            return "redirect:scorecard/"+model.getSongId();
        }

        this.songService.saveScore(principal.getName(), this.mapper.map(model, ScoreServiceModel.class));
        return "redirect:/jury/scenario/" + model.getEditionId();
    }

    private ScoreCardViewModel getScoreCardModel(Long songId, Principal principal) {
        ScoreServiceModel score = this.songService.getSongScore(songId, principal);
        return this.mapper.map(score, ScoreCardViewModel.class);
    }

    private Paged<EditionListViewModel> getEditionListViewModelPaged(Principal principal, int pageNumber, int size) {
        Page<EditionListViewModel> futureEditions =
                this.editionService
                        .getEditionsActiveForJuryMember(principal, pageNumber, size)
                        .map(esm -> this.mapper.map(esm, EditionListViewModel.class));

        return new Paged<>(futureEditions, Paging.of(futureEditions.getTotalPages(), pageNumber, size));
    }
}
