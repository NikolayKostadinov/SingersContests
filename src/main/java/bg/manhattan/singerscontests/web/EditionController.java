package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.entity.Edition;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.service.ContestServiceModel;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import bg.manhattan.singerscontests.model.view.ContestEditionsViewModel;
import bg.manhattan.singerscontests.model.view.ContestViewModel;
import bg.manhattan.singerscontests.model.view.EditionListViewModel;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/editions")
public class EditionController extends BaseController{
    private final EditionService editionService;
    private final ContestService contestService;
    private final UserService userService;
    private final ModelMapper mapper;

    public EditionController(EditionService editionService, ContestService contestService, UserService userService, ModelMapper mapper) {
        this.editionService = editionService;
        this.contestService = contestService;
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public String contests(Model model) {
        setFormTitle("Singers Contests - Contests", model);
        return "editions/contests";
    }

    @GetMapping("/{contestId}")
    public String editions(@PathVariable Long contestId, Model model) throws NotFoundException {
        setFormTitle("Singers Contests - Editions", model);
        initEditions(model, contestId);
        return "editions/editions";
    }

    private void initEditions(Model model, Long contestId) throws NotFoundException {
        if (!model.containsAttribute("editionsModel")){
            ContestServiceModelWithEditions contest = this.contestService.getContestByIdWithEditions(contestId);

            model.addAttribute("editionsModel",
                    this.mapper.map(contest, ContestEditionsViewModel.class));
        }
    }

    @ModelAttribute("contestList")
    public List<ContestViewModel> initContests(HttpServletRequest request, Principal principal) throws UserNotFoundException {
        return this.contestService
                .getAllContestsByContestManager(principal, request.isUserInRole(UserRoleEnum.ADMIN.name()))
                .stream()
                .map(contest -> this.mapper.map(contest, ContestViewModel.class))
                .collect(Collectors.toList());
    }
}
