package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.binding.JuryDemoteBindingModel;
import bg.manhattan.singerscontests.model.binding.JuryMemberCreateBindingModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.model.view.JuryDemoteViewModel;
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
    public String jury(@Valid JuryMemberCreateBindingModel juryModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juryModel", juryModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juryModel",
                    bindingResult);
            return "redirect:/administration/jury";
        }

        this.userService.createJuryMember(this.mapper.map(juryModel, JuryMemberServiceModel.class));
        return "redirect:/administration/jury";
    }

    @GetMapping("/demote")
    public String demodeJury(Model model) {
        setFormTitle("Singers Contests - Promote Jury", model);
        model.addAttribute("demote", "active");
        addJuryMembers(model);
        return "administration/demote-jury";
    }

    @PostMapping("/demote")
    public String demoteJury(@Valid JuryDemoteBindingModel juryDemoteModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juryDemoteModel", juryDemoteModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juryModel", bindingResult);
            return "redirect:/administration/jury/demote";
        }

        juryDemoteModel.getIdsToRemove()
                .stream()
                .distinct()
                .forEach(userId -> this.userService.removeUserFromRole(userId, UserRoleEnum.JURY_MEMBER));
        return "redirect:/administration/jury/demote";
    }

    @GetMapping("/edit")
    public String editJury(Model model) {
        setFormTitle("Singers Contests - Edit Jury details", model);
        model.addAttribute("edit", "active");
        addJuryToEdit(model);
        return "administration/edit-jury-details";
    }

    @PostMapping("/edit")
    public String edit(@Valid JuryMemberCreateBindingModel juryModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juryModel", juryModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juryModel",
                    bindingResult);
            return "redirect:/administration/jury/edit";
        }

        this.userService.editJuryMember(this.mapper.map(juryModel, JuryMemberServiceModel.class));
        return "redirect:/administration/jury/edit";
    }

    private void addJuryToEdit(Model model){
        if (!model.containsAttribute("juryMembers")) {
            model.addAttribute("juryMembers", getJuryMembers());
        }
    }

    private void addJuryMembers(Model model) {
        if (!model.containsAttribute("juryDemoteModel")) {
            JuryDemoteViewModel demoteMadel = new JuryDemoteViewModel()
                    .setUserRole(UserRoleEnum.JURY_MEMBER)
                    .setMembers(getJuryMembers());
            model.addAttribute("juryDemoteModel", demoteMadel);
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
    public JuryMemberCreateBindingModel initModel() {
        return new JuryMemberCreateBindingModel();
    }
}
