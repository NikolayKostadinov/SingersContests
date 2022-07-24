package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.binding.CreateJuryMemberBindingModel;
import bg.manhattan.singerscontests.model.binding.JuryDemodeBindingModel;
import bg.manhattan.singerscontests.model.binding.JuryMemberBindingModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.model.view.JuryDemodeViewModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.services.JuryMemberService;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.web.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/administration/jury")
public class AdminJuryController extends BaseController {
    private final JuryMemberService juryMemberService;
    private final UserService userService;

    private final ModelMapper mapper;

    public AdminJuryController(JuryMemberService juryMemberService,
                               UserService userService, ModelMapper mapper) {
        this.juryMemberService = juryMemberService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public String jury(Model model) {
        setFormTitle("Singers Contests - Promote Jury", model);
        model.addAttribute("promote", "active");
        addPotentialJuryMembers(model);
        return "administration/promote-jury";
    }

    @PostMapping
    public String jury(@Valid CreateJuryMemberBindingModel juryModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juryModel", juryModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juryModel",
                    bindingResult);
            return "redirect:/administration/jury";
        }

        this.userService.createJuryMember(this.mapper.map(juryModel, JuryMemberServiceModel.class));
        return "redirect:/administration/home";
    }

    @GetMapping("/demode")
    public String demodeJury(Model model) {
        setFormTitle("Singers Contests - Promote Jury", model);
        model.addAttribute("demode", "active");
        addJuryMembers(model);
        return "administration/demote-jury";
    }

    @PostMapping("/demode")
    public String demodeJury(@Valid JuryDemodeBindingModel juryDemodeModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juryDemodeModel", juryDemodeModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juryModel", bindingResult);
            return "redirect:/administration/jury/demote";
        }

        juryDemodeModel.getIdsToRemove()
                .stream()
                .distinct()
                .forEach(userId -> this.userService.removeUserFromRole(userId, UserRoleEnum.JURY_MEMBER));
        return "redirect:/administration/home";
    }

    @GetMapping("/edit")
    public String editJury(Model model) {
        setFormTitle("Singers Contests - Edit Jury details", model);
        model.addAttribute("edit", "active");
        addJuryToEdit(model);
        return "/administration/edit-jury-details";
    }

    @PostMapping("/edit")
    public String edit(@Valid CreateJuryMemberBindingModel juryModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juryModel", juryModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juryModel",
                    bindingResult);
            return "redirect:/administration/jury/edit";
        }

        this.userService.editJuryMember(this.mapper.map(juryModel, JuryMemberServiceModel.class));
        return "redirect:/administration/home";
    }

    private void addJuryToEdit(Model model){
        if (!model.containsAttribute("juryMembers")) {
            model.addAttribute("juryMembers", getJuryMembers());
        }
    }

    private void addJuryMembers(Model model) {
        if (!model.containsAttribute("juryDemodeModel")) {
            JuryDemodeViewModel demodeMadel = new JuryDemodeViewModel()
                    .setUserRole(UserRoleEnum.JURY_MEMBER)
                    .setMembers(getJuryMembers());
            model.addAttribute("juryDemodeModel", demodeMadel);
        }
    }

    private void addPotentialJuryMembers(Model model) {
        if (!model.containsAttribute("potentialJuryMembers")) {
            model.addAttribute("potentialJuryMembers", getPotentialJuryMembers());
        }
    }

    private List<UserSelectViewModel> getPotentialJuryMembers() {
        List<UserSelectViewModel> potentialJuryMembers =
                userService.getUserNotInRole(UserRoleEnum.JURY_MEMBER)
                        .stream()
                        .map(u -> this.mapper.map(u, UserSelectViewModel.class))
                        .toList();
        return potentialJuryMembers;
    }

    private List<UserSelectViewModel> getJuryMembers() {
        List<UserSelectViewModel> juryMembers = this.userService.getUsersByRole(UserRoleEnum.JURY_MEMBER)
                .stream()
                .map(user -> this.mapper.map(user, UserSelectViewModel.class))
                .toList();
        return juryMembers;
    }

    @ModelAttribute("juryModel")
    public CreateJuryMemberBindingModel initModel() {
        return new CreateJuryMemberBindingModel();
    }
}
