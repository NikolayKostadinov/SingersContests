package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.pageing.Paged;
import bg.manhattan.singerscontests.model.pageing.Paging;
import bg.manhattan.singerscontests.model.view.EditionListViewModel;
import bg.manhattan.singerscontests.model.view.RankingViewModel;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.SongService;
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
@RequestMapping("/ranking")
public class RankingController extends BaseController {
    private final EditionService editionService;

    private final ContestantService contestantService;

    private final SongService songService;
    private final ModelMapper mapper;

    public RankingController(EditionService editionService,
                             ContestantService contestantService,
                             SongService songService,
                             ModelMapper mapper) {
        this.editionService = editionService;
        this.contestantService = contestantService;
        this.songService = songService;
        this.mapper = mapper;
    }

    @GetMapping("/editions")
    @Transactional
    public String getEditions(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                           Model model) {
        setFormTitle("Singers Contests - Editions", model);
        if (!model.containsAttribute("editions")) {
            Paged<EditionListViewModel> viewModel = getEditionListViewModelPaged(pageNumber, size);
            model.addAttribute("editions", viewModel);
        }
        return "ranking/editions";
    }

    @GetMapping("/editions/{id}")
    @Transactional
    public String getEditionRanking(@PathVariable("id") Long id, @RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                                           @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                           Model model) {
        setFormTitle("Singers Contests - Ranking result", model);

        RankingViewModel rankingOrder =
                this.contestantService.getContestantsForEditionOrderedByAgeGroupAndScore(id);
        model.addAttribute("rankingOrder", rankingOrder);
        return "ranking/result";
    }

    private Paged<EditionListViewModel> getEditionListViewModelPaged(int pageNumber, int size) {
        Page<EditionListViewModel> finishedEditions =
                this.editionService
                        .getFinishedEditions(pageNumber, size)
                        .map(esm -> this.mapper.map(esm, EditionListViewModel.class));

        return new Paged<>(finishedEditions, Paging.of(finishedEditions.getTotalPages(), pageNumber, size));
    }
}
