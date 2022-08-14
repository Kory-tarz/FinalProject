package pl.cyryl.finalproject.exceptions;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class UserIdNotFoundException extends RuntimeException {

    public UserIdNotFoundException(String message){
        super(message);
    }
}
