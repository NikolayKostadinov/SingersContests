package bg.manhattan.singerscontests.web;

import bg.manhattan.singerscontests.model.enums.ResourceType;
import bg.manhattan.singerscontests.model.view.FileViewModel;
import bg.manhattan.singerscontests.services.CloudinaryService;
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

    @PostMapping("/upload")
    public ResponseEntity<FileViewModel> upload(MultipartFile file) {
        String resultUrl =
                this.cloudinaryService.uploadFile(file, file.getOriginalFilename(), ResourceType.image);
        return ResponseEntity.ok(new FileViewModel(resultUrl));
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
