package bg.manhattan.singerscontests.web;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController extends BaseController {

    @GetMapping
    public String index(Model model){
        setFormTitle("Singers Contests", model);
        return "index";
    }

    @GetMapping("/evaluation")
    public String evaluation(){
        throw new NotImplementedException();
    }

    @GetMapping("/ranking")
    public String ranking(){
        throw new NotImplementedException();
    }
}
