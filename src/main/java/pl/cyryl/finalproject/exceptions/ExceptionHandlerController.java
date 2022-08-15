package pl.cyryl.finalproject.exceptions;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(UserNotFoundInSessionException.class)
    public String sessionError(){
        return "redirect:/login";
    }

}
