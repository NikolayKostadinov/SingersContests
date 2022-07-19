package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.binding.UserLoginBindingModel;
import bg.manhattan.singerscontests.model.binding.UserRegisterBindingModel;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Controller
@RequestMapping("/authentication")
public class AuthenticationController extends BaseController {
    private final UserService userService;
    private final ModelMapper mapper;

    public AuthenticationController(UserService userService,
                                    ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/register")
    public String register(Model model) {
        setFormTitle("Singers Contests - User register", model);
        return "authentication/register";
    }

    @PostMapping("/register")
    public String register(@Valid UserRegisterBindingModel userModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) throws MessagingException {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("userModel", userModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userModel", bindingResult);

            return "redirect:register";
        }
        UserServiceModel model = this.mapper.map(userModel, UserServiceModel.class);
        this.userService.register(model);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "authentication/login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String userName,
            RedirectAttributes redirectAttributes) {

        redirectAttributes.addFlashAttribute("loginModel", new UserLoginBindingModel().setUsername(userName));
        redirectAttributes.addFlashAttribute("bad_credentials",
                true);

        return "redirect:login";
    }

    @ModelAttribute("userModel")
    public UserRegisterBindingModel initUserModel() {
        return new UserRegisterBindingModel();
    }

    @ModelAttribute("loginModel")
    public UserLoginBindingModel initLoginModel() {
        return new UserLoginBindingModel();
    }

}
