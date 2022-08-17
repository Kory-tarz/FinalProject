package pl.cyryl.finalproject.exceptions;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.NoSuchElementException;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundInSessionException.class)
    public String sessionError() {
        return "redirect:/login";
    }

    @ExceptionHandler(NoSuchElementException.class)
    public String noSuchElement(NoSuchElementException exception) {
        System.out.println(exception.getMessage());
        return "redirect:/notfound";
    }

    @ExceptionHandler(UnauthorizedAccessException.class)
    public String unauthorizedAccess(UnauthorizedAccessException exception){
        System.out.println(exception.getMessage());
        return "redirect:/403";
    }

}
