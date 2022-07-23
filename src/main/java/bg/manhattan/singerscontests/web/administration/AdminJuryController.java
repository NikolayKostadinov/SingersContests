package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.binding.CreateJuriMemberBindingModel;
import bg.manhattan.singerscontests.model.binding.JuriDemodeBindingModel;
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
        addPotentialJuriMembers(model);
        return "administration/promote-jury";
    }

    @PostMapping
    public String jury(@Valid CreateJuriMemberBindingModel juriModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juriModel", juriModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juriModel", bindingResult);
            return "redirect:/administration/juri";
        }

        this.userService.createJuryMember(this.mapper.map(juriModel, JuryMemberServiceModel.class));
        return "redirect:/administration/home";
    }

    @GetMapping("/demode")
    public String demodeJury(Model model) {
        setFormTitle("Singers Contests - Promote Jury", model);
        model.addAttribute("demode", "active");
        addJuriMembers(model);
        return "administration/demode-jury";
    }

    private void addJuriMembers(Model model) {
        if (!model.containsAttribute("juriDemodeModel")) {
            List<UserSelectViewModel> juryMembers = this.userService.getUsersByRole(UserRoleEnum.JURY_MEMBER)
                    .stream()
                    .map(user -> this.mapper.map(user, UserSelectViewModel.class))
                    .toList();
            JuryDemodeViewModel demodeMadel = new JuryDemodeViewModel()
                    .setUserRole(UserRoleEnum.JURY_MEMBER)
                    .setMembers(juryMembers);

            model.addAttribute("juriDemodeModel", demodeMadel);
        }
    }

    @PostMapping("/demode")
    public String demodeJury(@Valid JuriDemodeBindingModel juriDemodeModel,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("juriDemodeModel", juriDemodeModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.juriModel", bindingResult);
            return "redirect:/administration/juri";
        }

        juriDemodeModel.getIdsToRemove()
                .stream()
                .distinct()
                .forEach(userId -> this.userService.removeUserFromRole(userId, UserRoleEnum.JURY_MEMBER));
        return "redirect:/administration/home";
    }

    private void addPotentialJuriMembers(Model model) {
        if (!model.containsAttribute("potentialJuryMembers")) {
            List<UserSelectViewModel> potentialJuryMembers =
                    userService.getUserNotInRole(UserRoleEnum.JURY_MEMBER)
                            .stream()
                            .map(u -> this.mapper.map(u, UserSelectViewModel.class))
                            .toList();
            model.addAttribute("potentialJuryMembers", potentialJuryMembers);
        }
    }

    @ModelAttribute("juriModel")
    public CreateJuriMemberBindingModel initModel() {
        return new CreateJuriMemberBindingModel();
    }
}
