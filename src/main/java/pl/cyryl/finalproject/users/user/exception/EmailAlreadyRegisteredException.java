package pl.cyryl.finalproject.users.user.exception;

public class EmailAlreadyRegisteredException extends Exception {
    public EmailAlreadyRegisteredException(String message){
        super(message);
    }
}
