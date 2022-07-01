package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.binding.ContestCreateBindingModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.view.ContestViewModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contests")
public class ContestController extends BaseController {
    private final ContestService contestService;
    private final UserService userService;
    private final ModelMapper mapper;

    public ContestController(ContestService contestService,
                             UserService userService,
                             ModelMapper mapper) {
        this.contestService = contestService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/add")
    public String add(Model model) {
        setFormTitle("Singers Contests - Create Contest", model);

        if (!model.containsAttribute("contestManagers")) {
            List<UserSelectViewModel> contestManagers =
                    this.userService.getUsersByRole(UserRoleEnum.CONTEST_MANAGER)
                            .stream()
                            .map(user -> this.mapper.map(user, UserSelectViewModel.class))
                            .toList();

            model.addAttribute("contestManagers", contestManagers);
        }
        return "add-contest";
    }

    @PostMapping("/add")
    public String register(@Valid ContestCreateBindingModel contestModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("contestModel", contestModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contestModel", bindingResult);

            return "redirect:register";
        }
        //this.contestService.register(contestModel);
        return "redirect:/";
    }


    @GetMapping
    public String contests(Model model) {
        setFormTitle("Singers Contests - Contests", model);
        return "contests";
    }

    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {
        //todo: only if current user is contest manager
        this.contestService.delete(id);
        return "redirect:/contests";
    }

    @ModelAttribute("contestList")
    public List<ContestViewModel> initContests() {
        return this.contestService.getAllContests()
                .stream()
                .map(contest -> this.mapper.map(contest, ContestViewModel.class))
                .collect(Collectors.toList());
    }

    @ModelAttribute("contestModel")
    public ContestCreateBindingModel initContestCreate() {
        return new ContestCreateBindingModel();
    }
}
