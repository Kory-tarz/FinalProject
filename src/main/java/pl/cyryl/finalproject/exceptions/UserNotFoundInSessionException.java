package pl.cyryl.finalproject.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserNotFoundInSessionException extends RuntimeException {

    public UserNotFoundInSessionException(String message){
        super(message);
    }
}
