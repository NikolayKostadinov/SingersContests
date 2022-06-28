package bg.manhattan.singerscontests.web;

import org.springframework.ui.Model;

public abstract class BaseController {
    protected void setFormTitle(String title, Model model) {
        if (!model.containsAttribute("title")) {
            model.addAttribute("title", title);
        }
    }
}
