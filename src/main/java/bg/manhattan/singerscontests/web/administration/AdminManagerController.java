package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.services.UserRoleService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administration/managers")
public class AdminManagerController extends BaseRoleAdministrationController {

    public AdminManagerController(UserService userService, UserRoleService userRoleService, ModelMapper mapper) {
        super(UserRoleEnum.CONTEST_MANAGER, userService, userRoleService, mapper);
    }

    @GetMapping
    public String managers(Model model) {
        setFormTitle("Singers Contests - Promote Managers", model);
        return "administration/roles";
    }

    @Override
    public String getRoleDisplayName() {
        return "Contest Managers";
    }
}
