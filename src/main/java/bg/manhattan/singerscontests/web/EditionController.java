package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.AgeGroupBindingModel;
import bg.manhattan.singerscontests.model.binding.EditionCreateBindingModel;
import bg.manhattan.singerscontests.model.binding.JuryMemberBindingModel;
import bg.manhattan.singerscontests.model.binding.PerformanceCategoryBindingModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import bg.manhattan.singerscontests.model.view.ContestEditionsViewModel;
import bg.manhattan.singerscontests.model.view.ContestViewModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/editions")
public class EditionController extends BaseController {
    private final EditionService editionService;
    private final ContestService contestService;
    private final UserService userService;
    private final ModelMapper mapper;

    public EditionController(EditionService editionService, ContestService contestService, UserService userService, ModelMapper mapper) {
        this.editionService = editionService;
        this.contestService = contestService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public String contests(Model model) {
        setFormTitle("Singers Contests - Contests", model);
        return "editions/contests";
    }

    @GetMapping("/{contestId}")
    public String editions(@PathVariable Long contestId, Model model) throws NotFoundException {
        setFormTitle("Singers Contests - Editions", model);
        initEditions(model, contestId);
        return "editions/editions";
    }

    private void initEditions(Model model, Long contestId) throws NotFoundException {
        if (!model.containsAttribute("editionsModel")) {
            ContestServiceModelWithEditions contest = this.contestService.getContestByIdWithEditions(contestId);
            model.addAttribute("editionsModel",
                    this.mapper.map(contest, ContestEditionsViewModel.class));
        }
    }

    @GetMapping("/{contestId}/create")
    public String createEdition(@PathVariable("contestId") Long contestId, Model model) {
        setFormTitle("Singers Contests - Create edition", model);
        setEditionModel(contestId, model);
        addJuryMembersListToModel(model);
        return "editions/edition-create";
    }

    @PostMapping("/{contestId}/create")
    public String create(@PathVariable("contestId") Long contestId,
                         @Valid EditionCreateBindingModel editionModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Principal principal) throws UserNotFoundException {
        ensureSameContestId(editionModel, contestId, bindingResult);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editionModel", editionModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editionModel", bindingResult);

            return "redirect:/editions/" + contestId + "/create";
        }

        //this.contestService.create(this.mapper.map(contestModel, ContestCreateServiceModel.class));
        return "redirect:/contests";
    }

    private void ensureSameContestId(EditionCreateBindingModel editionModel,
                                     Long contestId,
                                     BindingResult bindingResult) {
        if (!editionModel.getContestId().equals(contestId)){
            bindingResult.addError(
                    new ObjectError("global",
                            "Invalid contest!"));
        }
    }


    private void setEditionModel(long contestId, Model model) {
        if (!model.containsAttribute("editionModel")) {
            model.addAttribute("editionModel",
                    new EditionCreateBindingModel()
                            .setContestId(contestId)
                            .setAgeGroups(Set.of(new AgeGroupBindingModel()))
                            .setPerformanceCategories(Set.of(new PerformanceCategoryBindingModel()))
                            );
        }
    }

    @ModelAttribute("contestList")
    public List<ContestViewModel> initContests(HttpServletRequest request, Principal principal) throws UserNotFoundException {
        return this.contestService
                .getAllContestsByContestManager(principal, request.isUserInRole(UserRoleEnum.ADMIN.name()))
                .stream()
                .map(contest -> this.mapper.map(contest, ContestViewModel.class))
                .collect(Collectors.toList());
    }

    private void addJuryMembersListToModel(Model model) {
        if (!model.containsAttribute("juryMembersList")) {
            List<UserSelectViewModel> juryMembers =
                    this.userService
                            .getUsersByRole(UserRoleEnum.JURY_MEMBER)
                            .stream()
                            .map(user -> this.mapper.map(user, UserSelectViewModel.class))
                            .toList();

            model.addAttribute("juryMembersList", juryMembers);
        }
    }
}
