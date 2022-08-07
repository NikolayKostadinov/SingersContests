package bg.manhattan.singerscontests.web.administration;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.model.service.JuryMemberServiceModel;
import bg.manhattan.singerscontests.model.view.ApiErrorViewModel;
import bg.manhattan.singerscontests.model.view.JuryDetailsViewModel;
import bg.manhattan.singerscontests.services.JuryMemberService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
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


    @Tag(name = "Get jury details by ID",
            description = "Returns the jury member's  full name, details and URL of his/her photo")
    @Parameter(name = "id",
            description = "The ID of the jury member",
            required = true)
    @ApiResponse(responseCode = "200", description = "If jury member's information was retrieved successfully")
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
