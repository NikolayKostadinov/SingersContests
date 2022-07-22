package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.binding.CreateJuriMemberBindingModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.services.JuryMemberService;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.web.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
        addPotentialJuriMembers(model);
        return "administration/promote-jury";
    }

    private void addPotentialJuriMembers(Model model) {
        if (!model.containsAttribute("potentialJuryMembers")) {
            List<UserSelectViewModel> potentialJuryMembers =
                    userService.getPotentialJuryMembers(UserRoleEnum.JURY_MEMBER)
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
