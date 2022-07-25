package bg.manhattan.singerscontests.web.controlleradvice;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.view.ErrorViewModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${spring.servlet.multipart.max-file-size:10MB}")
    private String maxFileSize;

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public ResponseEntity<ErrorViewModel> handleMaxUploadSizeExceededException(Exception ex) {
        String details = ex.getCause().getCause().getMessage();
        ErrorViewModel message =
                new ErrorViewModel("File too large!\n Maximum file size is " + maxFileSize, details);

        LOGGER.warn("{} {}", message.getMessage(), ex.getMessage());

        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE)
                .contentType(MediaType.APPLICATION_JSON)
                .body(message);
    }

    @ExceptionHandler({NotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleMaxUploadSizeExceededException(NotFoundException ex) {
        String details = ex.getMessage();
        ErrorViewModel model =
                new ErrorViewModel("Resource not found", details);
        ModelAndView result = new ModelAndView("error/error");
        result.addObject("error", model);
        LOGGER.warn("{} {}", model.getMessage(), model.getDetails());
        return result;
    }

    @ExceptionHandler({RuntimeException.class})
    public String handleUnhandledExceptions(RuntimeException ex) {
        LOGGER.warn("{} {}", ex.getMessage(), ex.getStackTrace());
        return "error/something-went-wrong";
    }
}
