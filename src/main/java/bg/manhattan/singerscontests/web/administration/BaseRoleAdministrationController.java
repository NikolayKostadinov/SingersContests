package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.binding.RoleEditBindingModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.UserRoleServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.view.RoleEditViewModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.services.UserRoleService;
import bg.manhattan.singerscontests.services.UserService;
import bg.manhattan.singerscontests.web.BaseController;
import org.modelmapper.ModelMapper;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

public abstract class BaseRoleAdministrationController extends BaseController {
    private final UserRoleEnum roleName;
    private final UserService userService;
    private final UserRoleService userRoleService;
    private final ModelMapper mapper;

    public BaseRoleAdministrationController(UserRoleEnum roleName,
                                            UserService userService,
                                            UserRoleService userRoleService,
                                            ModelMapper mapper) {
        this.roleName = roleName;
        this.userService = userService;
        this.userRoleService = userRoleService;
        this.mapper = mapper;
    }

    @PostMapping
    public String edit(@Valid RoleEditBindingModel roleModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes,
                       HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("roleModel", roleModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.roleModel", bindingResult);
            return "redirect:" + request.getRequestURL();
        }

        roleModel.getIdsToAdd()
                .stream()
                .distinct()
                .forEach(id -> this.userService.addUserInRole(id, this.roleName));

        roleModel.getIdsToDelete()
                .stream()
                .distinct()
                .forEach(id -> this.userService.removeUserFromRole(id, this.roleName));

        return "redirect:" + request.getRequestURL();
    }

    @ModelAttribute("managerModel")
    private RoleEditViewModel initModel() {
        UserRoleServiceModel role = this.userRoleService.getRoleByName(this.roleName);
        List<UserSelectViewModel> members =
                toViewModelList(this.userService.getUsersByRole(this.roleName));
        List<UserSelectViewModel> nonMembers =
                toViewModelList(this.userService.getUserNotInRole(this.roleName));
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

    @ModelAttribute("roleDisplayName")
    public abstract String getRoleDisplayName();
}
