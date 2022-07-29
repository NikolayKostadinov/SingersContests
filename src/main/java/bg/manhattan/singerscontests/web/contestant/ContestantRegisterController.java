package bg.manhattan.singerscontests.web.contestant;

import bg.manhattan.singerscontests.model.binding.ContestantCreateBindingModel;
import bg.manhattan.singerscontests.model.service.ContestantServiceModel;
import bg.manhattan.singerscontests.model.view.EditionContestantViewModel;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.web.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/contestants")
public class ContestantRegisterController extends BaseController {
    private final ContestantService contestantService;
    private final ModelMapper mapper;

    public ContestantRegisterController(ContestantService contestantService, ModelMapper mapper) {
        this.contestantService = contestantService;
        this.mapper = mapper;
    }

    @GetMapping("/register/{editionId}")
    public String register(@PathVariable("editionId") Long editionId,
                           Model model) {
        setFormTitle("Singers Contests - Register Contestant", model);
        initContestantModel(editionId, model);
        return "contestants/register";
    }



    private void initContestantModel(Long editionId, Model model) {
        if (!model.containsAttribute("contestantModel")) {
            ContestantServiceModel contestant = this.contestantService.getContestantModel(editionId);
            ContestantCreateBindingModel contestantModel = this.mapper.map(contestant, ContestantCreateBindingModel.class);
            model.addAttribute("contestantModel", contestantModel);
            model.addAttribute("contestModel", new EditionContestantViewModel()
                    .setContestName(contestant.getContestName())
                    .setNumber(contestant.getEditionNumber())
                    .setEditionType(contestant.getEditionType().name()));
        }
    }
}
