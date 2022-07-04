package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.PasswordNotMatchesException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.binding.EmailChangeBindingModel;
import bg.manhattan.singerscontests.model.binding.PasswordChangeBindingModel;
import bg.manhattan.singerscontests.model.binding.ProfileDetailsBindingModel;
import bg.manhattan.singerscontests.model.binding.UserDeleteBindingModel;
import bg.manhattan.singerscontests.model.service.UserServiceModel;
import bg.manhattan.singerscontests.model.service.UserServiceProfileDetailsModel;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.web.authentication.rememberme.AbstractRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/account")
public class AccountManageController extends BaseController {

    private final UserService userService;
    private final ModelMapper mapper;

    public AccountManageController(UserService userService,
                                   ModelMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        setFormTitle("Singers Contests - Manage account", model);
        model.addAttribute("profile", true);
        return "account-manage/profile-change";
    }

    @PostMapping("/profile")
    public String profile(@Valid ProfileDetailsBindingModel profileDetails,
                          BindingResult bindingResult,
                          RedirectAttributes redirectAttributes,
                          Principal principal,
                          HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("profileDetails", profileDetails);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.profileDetails", bindingResult);
            return "redirect:profile";
        }

        UserServiceProfileDetailsModel userModel = this.mapper.map(profileDetails, UserServiceProfileDetailsModel.class);

        try {
            this.userService.changeUserProfileDetails(principal.getName(), userModel);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        }

        logout(request, response);
        return "redirect:/authentication/login";
    }

    @GetMapping("/email")
    public String email(Model model) {
        setFormTitle("Singers Contests - Manage account", model);
        model.addAttribute("email", true);
        return "account-manage/email-change";
    }

    @PostMapping("/email")
    public String email(@Valid EmailChangeBindingModel emailModel,
                        BindingResult bindingResult,
                        RedirectAttributes redirectAttributes,
                        Principal principal) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("emailModel", emailModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.emailModel", bindingResult);
            return "redirect:email";
        }

        if (!emailModel.getEmail().equals(emailModel.getNewEmail())) {
            try {
                this.userService.changeUserEmail(principal.getName(), emailModel.getNewEmail());
            } catch (UserNotFoundException e) {
                throw new UsernameNotFoundException(e.getMessage());
            }
        }
        return "redirect:email";
    }

    @GetMapping("/password")
    public String password(Model model) {
        setFormTitle("Singers Contests - Manage account", model);
        model.addAttribute("changePassword", true);
        return "account-manage/password-change";
    }

    @PostMapping("/password")
    public String password(@Valid PasswordChangeBindingModel passwordModel,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes,
                           Principal principal,
                           HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            return getPasswordErrorResponse(passwordModel, bindingResult, redirectAttributes);
        }

        if (!passwordModel.getNewPassword().equals(passwordModel.getCurrentPassword())) {
            try {
                this.userService.changeUserPassword(
                        principal.getName(),
                        passwordModel.getCurrentPassword(),
                        passwordModel.getNewPassword());
            } catch (UserNotFoundException e) {
                throw new UsernameNotFoundException(e.getMessage());
            } catch (PasswordNotMatchesException e) {
                bindingResult.addError(
                        new ObjectError("badCredentials",
                                "Incorrect password"));
                return getPasswordErrorResponse(passwordModel, bindingResult, redirectAttributes);
            }
        }

        logout(request, response);
        return "redirect:/users/login";
    }


    @GetMapping("/delete_personal_data")
    public String deletePersonalData(Model model) {
        setFormTitle("Singers Contests - Manage account", model);
        model.addAttribute("personalData", true);
        return "account-manage/account-delete";
    }

    @PostMapping("/delete_personal_data")
    public String deletePersonalData(@Valid UserDeleteBindingModel deleteUserModel,
                                     BindingResult bindingResult,
                                     RedirectAttributes redirectAttributes,
                                     Principal principal,
                                     HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("deleteUserModel", deleteUserModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteUserModel", bindingResult);
            return "redirect:delete_personal_data";
        }
        try {
            this.userService.deleteUser(principal.getName(), deleteUserModel.getPassword());
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (PasswordNotMatchesException e) {
            bindingResult.addError(
                    new ObjectError("password",
                            "Incorrect password"));
            redirectAttributes.addFlashAttribute("deleteUserModel", deleteUserModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.deleteUserModel", bindingResult);
            return "redirect:delete_personal_data";
        }

        logout(request, response);
        return "redirect:/users/login";
    }


    public static void logout(HttpServletRequest request, HttpServletResponse response) {
        CookieClearingLogoutHandler cookieClearingLogoutHandler =
                new CookieClearingLogoutHandler(AbstractRememberMeServices.SPRING_SECURITY_REMEMBER_ME_COOKIE_KEY);
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        cookieClearingLogoutHandler.logout(request, response, null);
        securityContextLogoutHandler.logout(request, response, null);
    }

    private String getPasswordErrorResponse(PasswordChangeBindingModel passwordModel,
                                            BindingResult bindingResult,
                                            RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("passwordModel", passwordModel);
        redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.passwordModel", bindingResult);
        return "redirect:password";
    }

    @ModelAttribute("profileDetails")
    public ProfileDetailsBindingModel initProfileDetails(Principal principal) {

        UserServiceModel user = this.userService
                .getUserByUsername(principal.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User " + principal.getName() + "not found!"));

        return this.mapper.map(user, ProfileDetailsBindingModel.class);
    }

    @ModelAttribute("emailModel")
    public EmailChangeBindingModel initEmailModel(Principal principal) {
        UserServiceModel user = this.userService
                .getUserByUsername(principal.getName())
                .orElseThrow(() ->
                        new UsernameNotFoundException("User " + principal.getName() + "not found!"));

        return this.mapper.map(user, EmailChangeBindingModel.class);
    }

    @ModelAttribute("passwordModel")
    public PasswordChangeBindingModel initPasswordModel() {
        return new PasswordChangeBindingModel();

    }

    @ModelAttribute("deleteUserModel")
    public UserDeleteBindingModel initPassword() {
        return new UserDeleteBindingModel();
    }
}
