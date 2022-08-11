package pl.cyryl.finalproject.app.photo.exceptions;

import java.io.IOException;

public class NoPhotoException extends IOException {
    public NoPhotoException(String message){
        super(message);
    }
}
