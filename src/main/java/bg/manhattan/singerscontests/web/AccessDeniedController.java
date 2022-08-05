package bg.manhattan.singerscontests.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access-denied")
public class AccessDeniedController extends BaseController {
    @GetMapping
    public String accessDenied(Model model){
        setFormTitle("Singers Contests - Access denied", model);
        return "error/403";
    }
}
