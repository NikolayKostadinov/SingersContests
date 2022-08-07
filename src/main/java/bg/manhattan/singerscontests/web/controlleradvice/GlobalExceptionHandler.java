package bg.manhattan.singerscontests.web.controlleradvice;

import bg.manhattan.singerscontests.exceptions.NotFoundException;
import bg.manhattan.singerscontests.exceptions.UserNotFoundException;
import bg.manhattan.singerscontests.model.view.ErrorViewModel;
import org.apache.commons.lang3.NotImplementedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.TransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.ModelAndView;

import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @Value("${spring.servlet.multipart.max-file-size:10MB}")
    private String maxFileSize;

    @ExceptionHandler({MaxUploadSizeExceededException.class})
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public @ResponseBody ErrorViewModel handleMaxUploadSizeExceededException(MaxUploadSizeExceededException ex) {
        String details = ex.getMostSpecificCause().getMessage();
        ErrorViewModel message =
                new ErrorViewModel("File too large!\n Maximum file size is " + maxFileSize, details);

        LOGGER.warn("{} {}", message.getMessage(), ex.getMessage());

        return message;
    }

    @ExceptionHandler({NotFoundException.class, UserNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNotFoundException(Exception ex) {
        String details = ex.getMessage();
        ErrorViewModel model =
                new ErrorViewModel("Resource not found", details);
        ModelAndView result = new ModelAndView("error/error");
        result.addObject("error", model);
        LOGGER.warn("{} {}", model.getMessage(), model.getDetails());
        return result;
    }

        @ExceptionHandler({TransactionException.class, PersistenceException.class})
        public ModelAndView handleDatabaseErrors(RuntimeException ex) {
            String details = ex.getMessage();
            ErrorViewModel model =
                    new ErrorViewModel("We experience some database issues", details);
            ModelAndView modelAndView = new ModelAndView("index");
            modelAndView.addObject("message", ex.getMessage());
            LOGGER.warn("{} {}", ex.getMessage(), ex.getStackTrace());
            ModelAndView result = new ModelAndView("error/error");
            return modelAndView;
        }


    @ExceptionHandler({NotImplementedException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleNotImplementedException(NotImplementedException ex) {
        LOGGER.warn("{} {}", ex.getMessage(), ex.getStackTrace());
        return "error/not-implemented";
    }

    @ExceptionHandler({AccessDeniedException.class})
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public String handleUnhandledExceptions(HttpServletRequest request, HttpServletResponse response,
                                            AccessDeniedException ex) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LOGGER.warn("User '" + authentication.getName()
                    + "' attempt to access the URL: "
                    + request.getRequestURL());
        }
        return "error/403";
    }

    @ExceptionHandler({RuntimeException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleUnhandledExceptions(RuntimeException ex) {
        LOGGER.warn("{} {}", ex.getMessage(), ex.getStackTrace());
        return "error/something-went-wrong";
    }
}
