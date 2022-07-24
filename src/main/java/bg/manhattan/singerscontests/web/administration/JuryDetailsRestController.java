package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.model.view.ApiErrorViewModel;
import bg.manhattan.singerscontests.model.view.JuryDetailsViewModel;
import bg.manhattan.singerscontests.services.JuryMemberService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jury")
public class JuryDetailsRestController {

    private final JuryMemberService juryMemberService;
    private final ModelMapper mapper;

    public JuryDetailsRestController(JuryMemberService juryMemberService, ModelMapper mapper) {
        this.juryMemberService = juryMemberService;
        this.mapper = mapper;
    }


    @GetMapping("/{id}")
    public ResponseEntity<JuryDetailsViewModel> getDetails(@PathVariable("id") Long id) {
        JuryMemberServiceModel juryMember = this.juryMemberService.getJuryMemberById(id);
        return ResponseEntity
                .ok(this.mapper.map(juryMember, JuryDetailsViewModel.class)
                        .setFullName(juryMember.getUser().getFullName()));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({NotFoundException.class})
    public @ResponseBody ApiErrorViewModel handleRESTErrors(NotFoundException e) {
        return new ApiErrorViewModel(e.getId(), e.getMessage());
    }
}
