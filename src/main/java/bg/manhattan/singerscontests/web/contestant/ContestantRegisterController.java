package bg.manhattan.singerscontests.web.contestant;

import bg.manhattan.singerscontests.model.binding.ContestantCreateBindingModel;
import bg.manhattan.singerscontests.model.service.ContestantServiceModel;
import bg.manhattan.singerscontests.model.view.EditionContestantViewModel;
import bg.manhattan.singerscontests.services.ContestantService;
import bg.manhattan.singerscontests.web.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.Positive;
import java.security.Principal;

@Controller
@RequestMapping("/contestants")
@Transactional
public class ContestantRegisterController extends BaseController {
    private final ContestantService contestantService;
    private final ModelMapper mapper;

    public ContestantRegisterController(ContestantService contestantService, ModelMapper mapper) {
        this.contestantService = contestantService;
        this.mapper = mapper;
    }

    @GetMapping("/register/{editionId}")
    public String register(@PathVariable("editionId") @Positive @Min(1) Long editionId,
                           Model model) {
        setFormTitle("Singers Contests - Register Contestant", model);
        if (!(model.containsAttribute("contestantModel")
                && model.containsAttribute("contestModel"))) {
            ContestantServiceModel contestant = this.contestantService.getContestantModel(editionId);
            initContestantModel(contestant, model);
            initContest(contestant, model);
        }
        return "contestants/register";
    }

    @PostMapping("/register/{editionId}")
    public String register(@Valid ContestantCreateBindingModel contestantModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contestantModel", contestantModel);
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.contestantModel", bindingResult);
            return "redirect:/contestants/register/" + contestantModel.getEditionId();
        }

        ContestantServiceModel contestant = this.mapper.map(contestantModel, ContestantServiceModel.class);
        this.contestantService.create(contestant, principal);
        return "redirect:/contestants/editions/details/"+ contestantModel.getEditionId();
    }


    private void initContestantModel(ContestantServiceModel contestant, Model model) {
        if (!model.containsAttribute("contestantModel")) {
            ContestantCreateBindingModel contestantModel = this.mapper.map(contestant, ContestantCreateBindingModel.class);
            model.addAttribute("contestantModel", contestantModel);
        }
    }

    private void initContest(ContestantServiceModel contestant, Model model) {
        if (!model.containsAttribute("contestModel")) {
            model.addAttribute("contestModel", new EditionContestantViewModel()
                    .setContestName(contestant.getContestName())
                    .setNumber(contestant.getEditionNumber())
                    .setEditionType(contestant.getEditionType().name()));
        }
    }
}
