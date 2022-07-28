package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.model.service.AgeGroupServiceModel;
import bg.manhattan.singerscontests.services.AgeGroupService;
import bg.manhattan.singerscontests.services.CloudinaryService;
import bg.manhattan.singerscontests.services.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

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
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.getModel().put("message", "File too large!");
        return modelAndView;
    }

    @GetMapping("/test/{editionId}/{birthDate}")
    public String home(Model model,
                       @PathVariable("editionId") Long editionId,
                       @PathVariable("birthDate") @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate birthDate) {
        AgeGroupServiceModel ageGroup = this.ageGroupService.getAgeGroup(editionId, birthDate);
        model.addAttribute("ageGroup", ageGroup);
        return "test";
    }
}
