package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.binding.RoleEditBindingModel;
import bg.manhattan.singerscontests.model.view.RoleEditViewModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserRoleServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.services.UserRoleService;
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
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/administration/managers")
public class AdminManagerController extends BaseController {

    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ModelMapper mapper;

    public AdminManagerController(UserService userService,
                                  UserRoleService userRoleService, ModelMapper mapper) {
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.mapper = mapper;
    }

    @GetMapping
    public String managers(Model model) {
        setFormTitle("Singers Contests - Promote Managers", model);
        return "administration/managers-administration";
    }

    @PostMapping
    public String Edit(@Valid RoleEditBindingModel roleModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("roleModel", roleModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.roleModel", bindingResult);
            return "redirect:/administration/managers";
        }

        roleModel.getIdsToAdd()
                .stream()
                .distinct()
                .forEach(id->this.userService.addUserInRole(id, UserRoleEnum.CONTEST_MANAGER));

        roleModel.getIdsToDelete()
                .stream()
                .distinct()
                .forEach(id->this.userService.removeUserFromRole(id, UserRoleEnum.CONTEST_MANAGER));

        return "redirect:/administration/home";
    }

    @ModelAttribute("managerModel")
    private RoleEditViewModel initModel() {
        UserRoleServiceModel role = this.userRoleService.getRoleByName(UserRoleEnum.CONTEST_MANAGER);
        List<UserSelectViewModel> members =
                toViewModelList(this.userService.getUsersByRole(UserRoleEnum.CONTEST_MANAGER));
        List<UserSelectViewModel> nonMembers =
                toViewModelList(this.userService.getUserNotInRole(UserRoleEnum.CONTEST_MANAGER));
        return new RoleEditViewModel()
                .setUserRole(role.getUserRole())
                .setMembers(members)
                .setNonMembers(nonMembers);
    }

    private List<UserSelectViewModel> toViewModelList(List<UserServiceModel> fromService) {
        return fromService
                .stream()
                .map(u -> this.mapper.map(u, UserSelectViewModel.class))
                .toList();
    }
}
