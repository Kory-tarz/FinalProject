package pl.cyryl.finalproject.users.authentication;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import pl.cyryl.finalproject.users.user.User;

import java.util.Locale;

@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent {
    private String appUrl;
    private User user;

    public OnRegistrationCompleteEvent(User user, String appUrl) {
        super(user);
        this.user = user;
        this.appUrl = appUrl;
    }
}
