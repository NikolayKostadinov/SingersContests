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
@RequestMapping("/administration/administrators")
public class AdminAdministratorsController extends BaseRoleAdministrationController {


    public AdminAdministratorsController(UserService userService,
                                         UserRoleService userRoleService,
                                         ModelMapper mapper) {
        super(UserRoleEnum.ADMIN, userService, userRoleService, mapper);
    }

    @GetMapping
    public String managers(Model model) {
        setFormTitle("Singers Contests - Promote Administrators", model);
        return "administration/roles";
    }

    @Override
    public String getRoleDisplayName() {
        return "Administrators";
    }
}
