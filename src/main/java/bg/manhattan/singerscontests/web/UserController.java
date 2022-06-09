package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.dto.UserRegisterDto;
import bg.manhattan.singerscontests.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static bg.manhattan.singerscontests.model.ModelConstants.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        initializeRegisterModel(model);
        return "user-register";
    }

    private void initializeRegisterModel(Model model) {
        model.addAttribute("title", "Singers Contests - User register");
        model.addAttribute("NAME_MAX_LENGTH", NAME_MAX_LENGTH);
        model.addAttribute("USER_NAME_MIN_LENGTH", USER_NAME_MIN_LENGTH);
        model.addAttribute("USER_NAME_MAX_LENGTH", USER_NAME_MAX_LENGTH);
        if (!model.containsAttribute("userModel")) {
            model.addAttribute("userModel", new UserRegisterDto());
        }
    }

    /**
     * BindingResult must be right after validated model otherwise doesn't work!
     */
    @PostMapping("/register")
    public String register(@Valid UserRegisterDto userModel,
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
}
