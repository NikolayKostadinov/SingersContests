package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        setFormTitle("Singers Contests - User register", model);
        return "user-register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:/users/register";
        }
        this.userService.register(userModel);
        return "redirect:/";
    }

    @ModelAttribute("userModel")
    public UserRegisterBindingModel initUserModel(){
        return new UserRegisterBindingModel();
    }
}
