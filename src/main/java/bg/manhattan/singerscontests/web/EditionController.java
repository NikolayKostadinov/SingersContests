package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.binding.AgeGroupBindingModel;
import bg.manhattan.singerscontests.model.binding.EditionCreateBindingModel;
import bg.manhattan.singerscontests.model.binding.EditionEditBindingModel;
import bg.manhattan.singerscontests.model.binding.PerformanceCategoryBindingModel;
import bg.manhattan.singerscontests.model.enums.UserRoleEnum;
import bg.manhattan.singerscontests.model.pageing.Paged;
import bg.manhattan.singerscontests.model.pageing.Paging;
import bg.manhattan.singerscontests.model.service.ContestServiceModelWithEditions;
import bg.manhattan.singerscontests.model.service.EditionServiceModel;
import bg.manhattan.singerscontests.model.view.ContestEditionsViewModel;
import bg.manhattan.singerscontests.model.view.ContestViewModel;
import bg.manhattan.singerscontests.model.view.UserSelectViewModel;
import bg.manhattan.singerscontests.services.ContestService;
import bg.manhattan.singerscontests.services.EditionService;
import bg.manhattan.singerscontests.services.JuryMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/editions")
public class EditionController extends BaseController {
    private final EditionService editionService;
    private final ContestService contestService;
    private final ModelMapper mapper;
    private final JuryMemberService juryMemberService;

    public EditionController(EditionService editionService,
                             ContestService contestService,
                             ModelMapper mapper,
                             JuryMemberService juryMembersService) {
        this.editionService = editionService;
        this.contestService = contestService;
        this.mapper = mapper;
        this.juryMemberService = juryMembersService;
    }

    @GetMapping
    public String contests(@RequestParam(value = "pageNumber", required = false, defaultValue = "1") int pageNumber,
                           @RequestParam(value = "size", required = false, defaultValue = "7") int size,
                           Model model,
                           HttpServletRequest request,
                           Principal principal) {
        setFormTitle("Singers Contests - Contests", model);
        addContestsToModel(model, pageNumber, size, request, principal);
        return "editions/contests";
    }

    @GetMapping("/{contestId}")
    public String editions(@PathVariable Long contestId, Model model) {
        setFormTitle("Singers Contests - Editions", model);
        initEditions(model, contestId);
        return "editions/editions";
    }

    private void initEditions(Model model, Long contestId) {
        if (!model.containsAttribute("editionsModel")) {
            ContestServiceModelWithEditions contest = this.contestService.getContestByIdWithEditions(contestId);
            model.addAttribute("editionsModel",
                    this.mapper.map(contest, ContestEditionsViewModel.class));
        }
    }

    @GetMapping("/{contestId}/create")
    public String createEdition(@PathVariable("contestId") Long contestId, Model model) {
        setFormTitle("Singers Contests - Create edition", model);
        setEditionModel(contestId, model);
        addJuryMembersListToModel(model);
        return "editions/edition-create";
    }

    @PostMapping("/{contestId}/create")
    public String create(@PathVariable("contestId") Long contestId,
                         @Valid EditionCreateBindingModel editionModel,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        ensureSameContestId(editionModel, contestId, bindingResult);
        filterDeleted(editionModel);
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editionModel", editionModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editionModel", bindingResult);
            return "redirect:/editions/" + contestId + "/create";
        }

        this.editionService.insert(this.mapper.map(editionModel, EditionServiceModel.class));
        return "redirect:/editions/" + contestId;
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id,
                       Model model) throws NotFoundException {
        setFormTitle("Singers Contests - Create edition", model);
        readEditionModel(id, model);
        addJuryMembersListToModel(model);
        return "editions/edition-edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid EditionEditBindingModel editionModel,
                       BindingResult bindingResult,
                       RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("editionModel", editionModel);
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.editionModel", bindingResult);
            return "redirect:/editions/edit/" + editionModel.getId();
        }

        this.editionService.insert(this.mapper.map(editionModel, EditionServiceModel.class));
        return "redirect:/editions/" + editionModel.getContestId();
    }

    @DeleteMapping("/delete/{contestId}/{id}")
    public String deleteEdition(@PathVariable("contestId") Long contestId,
                                @PathVariable("id") Long id) {
        this.editionService.delete(id);
        return "redirect:/editions/" + contestId;
    }

    private void readEditionModel(Long editionId, Model model){
        if (!model.containsAttribute("editionModel")) {
            EditionServiceModel edition = this.editionService.getById(editionId);
            EditionEditBindingModel editionModel = this.mapper.map(edition, EditionEditBindingModel.class);
            model.addAttribute("editionModel", editionModel);
        }
    }

    private void filterDeleted(EditionCreateBindingModel editionModel) {
        editionModel.setPerformanceCategories(
                editionModel.getPerformanceCategories()
                        .stream().filter(p -> !p.isDeleted())
                        .toList());
        editionModel.setAgeGroups(
                editionModel.getAgeGroups()
                        .stream().filter(a -> !a.isDeleted())
                        .toList());
    }

    private void ensureSameContestId(EditionCreateBindingModel editionModel,
                                     Long contestId,
                                     BindingResult bindingResult) {
        if (!editionModel.getContestId().equals(contestId)) {
            bindingResult.addError(
                    new ObjectError("global",
                            "Invalid contest!"));
        }
    }


    private void setEditionModel(long contestId, Model model){
        if (!model.containsAttribute("editionModel")) {
            EditionCreateBindingModel editionModel = new EditionCreateBindingModel()
                    .setContestId(contestId)
                    .setContestName(this.contestService.getContestById(contestId).getName());
            editionModel.getPerformanceCategories().add(new PerformanceCategoryBindingModel());
            editionModel.getAgeGroups().add(new AgeGroupBindingModel());
            model.addAttribute("editionModel", editionModel);
        }
    }

    public void addContestsToModel(Model model, int pageNumber, int size, HttpServletRequest request, Principal principal) {
        if (!model.containsAttribute("contestList")) {
            Page<ContestViewModel> contests = this.contestService
                    .getAllContestsByContestManager(principal, request.isUserInRole(UserRoleEnum.ADMIN.name()), pageNumber, size)
                    .map(contest -> this.mapper.map(contest, ContestViewModel.class));
            model.addAttribute("contestList", new Paged<>(contests, Paging.of(contests.getTotalPages(), pageNumber, size)));
        }
    }

    private void addJuryMembersListToModel(Model model) {
        if (!model.containsAttribute("juryMembersList")) {
            List<UserSelectViewModel> juryMembers =
                    this.juryMemberService
                            .getAll()
                            .stream()
                            .map(member -> this.mapper.map(member, UserSelectViewModel.class)
                                    .setFullName(member.getUser().getFullName()))
                            .toList();

            model.addAttribute("juryMembersList", juryMembers);
        }
    }
}
