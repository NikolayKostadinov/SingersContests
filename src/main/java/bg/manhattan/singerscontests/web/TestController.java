package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.binding.FileUploadBindingModel;
import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.entity.User;
import bg.manhattan.singerscontests.model.enums.ResourceType;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.CloudinaryService;
import bg.manhattan.singerscontests.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDate;

@Controller
public class TestController {

    private final UserService userService;
    private final CloudinaryService cloudinaryService;
    private final AgeGroupService ageGroupService;

    public TestController(UserService userService, CloudinaryService cloudinaryService, AgeGroupService ageGroupService) {
        this.userService = userService;
        this.cloudinaryService = cloudinaryService;
        this.ageGroupService = ageGroupService;
    }

    @GetMapping("/err")
    public ModelAndView errorPage(){
        ModelAndView modelAndView = new ModelAndView("error/upload-file-error");
        modelAndView.getModel().put("message", "File too large!");
        return modelAndView;
    }

    @GetMapping("/test")
    public String home(Model model) {
        AgeGroup ageGroup = this.ageGroupService.getAgeGroup(1, LocalDate.of(2009, 4, 25));
        model.addAttribute("ageGroup", ageGroup);
        return "test";
    }

    @GetMapping("/pictures")
    public String uploadPicture(Model model) {
        if (!model.containsAttribute("uploadModel")) {
            model.addAttribute("uploadModel", new FileUploadBindingModel());
        }
        return "test/upload-picture";
    }

    @PostMapping("/pictures")
    public String uploadGif(@Valid FileUploadBindingModel uploadModel,
                            Principal principal,
                            BindingResult bindingResult,
                            RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("uploadModel", uploadModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.uploadModel", bindingResult);
            return "test/upload-picture";
        }
        User currentUser = this.userService.getCurrentUser(principal);
        String url = cloudinaryService.uploadFile(uploadModel.getFile(), uploadModel.getTitle(), ResourceType.raw);
//        cloudinaryGifService.saveGifToDB(url, model.getTitle() , currentUser);
        return url;
    }

}
