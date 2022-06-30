package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.view.ContestViewModel;
import bg.manhattan.singerscontests.services.ContestService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/contests")
public class ContestController extends BaseController {
    private final ContestService contestService;
    private final ModelMapper mapper;

    public ContestController(ContestService contestService, ModelMapper mapper) {
        this.contestService = contestService;
        this.mapper = mapper;
    }

    @GetMapping
    public String contests(Model model){
        setFormTitle("Singers Contests - Contests", model);
        return "contests";
    }

    @ModelAttribute("contestList")
    public List<ContestViewModel> initContests(){
        return this.contestService.getAllContests()
                .stream()
                .map(contest -> this.mapper.map(contest, ContestViewModel.class))
                .collect(Collectors.toList());
    }
}
