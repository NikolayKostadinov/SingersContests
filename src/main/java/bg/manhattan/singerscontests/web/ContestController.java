package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.ContestCreateBindingModel;
import bg.manhattan.singerscontests.model.binding.ContestEditBindingModel;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.ContestCreateServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.model.view.ContestViewModel;
import bg.manhattan.singerscontests.model.view.ManagerViewModel;
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
import java.security.Principal;
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

    @GetMapping("/create")
    public String create(Model model) {
        setFormTitle("Singers Contests - Edit Contest", model);
        addManagersListToModel(model);
        return "contest-create";
    }

    @PostMapping("/create")
    public String create(@Valid ContestCreateBindingModel contestModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal) throws UserNotFoundException {
        if (bindingResult.hasErrors()) {
            ensureOneManager(contestModel.getManagers(), principal);
            redirectAttributes.addFlashAttribute("contestModel", contestModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contestModel", bindingResult);

            return "redirect:create";
        }

        this.contestService.create(this.mapper.map(contestModel, ContestCreateServiceModel.class));
        return "redirect:/contests";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Long id) throws NotFoundException {
        setFormTitle("Singers Contests - Create Contest", model);
        setContest(model, id);
        addManagersListToModel(model);
        return "contest-edit";
    }

    private void setContest(Model model, Long id) throws NotFoundException {
        if (!model.containsAttribute("contestEditModel")) {
            ContestServiceModel contest = this.contestService.getContestById(id);
            ContestEditBindingModel contestModel = this.mapper.map(contest, ContestEditBindingModel.class);
            model.addAttribute("contestEditModel", contestModel);
        }
    }

    @PostMapping("/edit")
    public String register(@Valid ContestEditBindingModel contestEditModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal) throws UserNotFoundException {
        if (bindingResult.hasErrors()) {
            ensureOneManager(contestEditModel.getManagers(), principal);
            redirectAttributes.addFlashAttribute("contestEditModel", contestEditModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.contestEditModel", contestEditModel);

            return "redirect:/contests/edit/" + contestEditModel.getId();
        }

        //this.contestService.update(this.mapper.map(contestEditModel, ContestCreateServiceModel.class));
        return "redirect:/contests";
    }

    private void addManagersListToModel(Model model) {
        if (!model.containsAttribute("contestManagers")) {
            List<UserSelectViewModel> contestManagers =
                    this.userService.getUsersByRole(UserRoleEnum.CONTEST_MANAGER)
                            .stream()
                            .map(user -> this.mapper.map(user, UserSelectViewModel.class))
                            .toList();

            model.addAttribute("contestManagers", contestManagers);
        }
    }

    private void ensureOneManager(List<Long> managers, Principal principal) throws UserNotFoundException {
        if (managers.size() == 0) {
            User currentUser = this.userService.getCurrentUser(principal);
            managers.add(currentUser.getId());
        }
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
    public ContestCreateBindingModel initContestCreate(Principal principal) throws UserNotFoundException {
        User currentUser = this.userService.getCurrentUser(principal);
        ContestCreateBindingModel model = new ContestCreateBindingModel();
        model.getManagers().add(currentUser.getId());
        return model;
    }

    @ModelAttribute("contestManagers")
    public List<ManagerViewModel> initManagers() {
        return this.userService.getUsersByRole(UserRoleEnum.CONTEST_MANAGER)
                .stream()
                .map(user -> this.mapper.map(user, ManagerViewModel.class))
                .toList();
    }
}
