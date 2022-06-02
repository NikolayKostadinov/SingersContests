package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.entity.AgeGroup;
import bg.manhattan.singerscontests.services.AgeGroupService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;

@Controller
public class TestController {
    private final AgeGroupService ageGroupService;

    public TestController(AgeGroupService ageGroupService) {
        this.ageGroupService = ageGroupService;
    }

    @GetMapping("/test")
    public String home(Model model){
        AgeGroup ageGroup = this.ageGroupService.getAgeGroup(1, LocalDate.of(2009, 4, 25));
        model.addAttribute("ageGroup", ageGroup);
        return "test";
    }


}
