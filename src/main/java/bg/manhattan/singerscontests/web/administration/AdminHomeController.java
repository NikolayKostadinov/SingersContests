package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.web.BaseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/administration")
public class AdminHomeController extends BaseController {
    @GetMapping("/home")
    public String home(Model model){
        setFormTitle("Singers Contests - Administration", model);
        return "administration/home";
    }
}
