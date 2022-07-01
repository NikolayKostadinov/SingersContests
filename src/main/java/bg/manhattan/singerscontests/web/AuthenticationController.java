package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.binding.UserLoginBindingModel;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/users")
public class AuthenticationController extends BaseController{
    @GetMapping("/login")
    public String login() {
        return "auth-login";
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

    @ModelAttribute("loginModel")
    public UserLoginBindingModel initLoginModel() {
        return new UserLoginBindingModel();
    }

}
