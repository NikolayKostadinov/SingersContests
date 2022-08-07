package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.enums.ResourceType;
import bg.manhattan.singerscontests.model.view.FileViewModel;
import bg.manhattan.singerscontests.services.CloudinaryService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/file")
public class FileTransferRestController {

    private final CloudinaryService cloudinaryService;

    public FileTransferRestController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    @Tag(name = "Upload file",
            description = "Uploads file content to the Cloudinary")
    @Parameter(name = "file",
            description = "A representation of a file received in a multipart request.",
            required = true)
    @ApiResponse(responseCode = "200", description = "If file was uploaded successfully")
    @PostMapping("/upload")
    public ResponseEntity<FileViewModel> upload(MultipartFile file) {
        String resultUrl =
                this.cloudinaryService.uploadFile(file, file.getOriginalFilename(), ResourceType.getType(file.getContentType()));
        return ResponseEntity.ok(new FileViewModel(resultUrl, file.getOriginalFilename()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
