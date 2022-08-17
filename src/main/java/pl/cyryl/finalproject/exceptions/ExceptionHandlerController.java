package pl.cyryl.finalproject.exceptions;

import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@Log4j2
@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundInSessionException.class)
    public String sessionError(UserNotFoundInSessionException exception) {
        log.error(exception.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElement(NoSuchElementException exception) {
        log.warn(exception.getMessage());
        return "redirect:/notfound";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String unauthorizedAccess(UnauthorizedAccessException exception){
        log.warn(exception.getMessage());
        return "redirect:/403";
    }

}
