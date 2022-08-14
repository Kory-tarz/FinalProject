package pl.cyryl.finalproject.users.user;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePicture;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePictureService;
import pl.cyryl.finalproject.users.user.exception.EmailAlreadyRegisteredException;
import pl.cyryl.finalproject.users.user.exception.UserNotFoundException;
import pl.cyryl.finalproject.users.user.verification.VerificationToken;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.net.http.HttpRequest;
import java.util.List;
import java.util.Optional;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    private final String USER_ATTRIBUTE = "user";
    private final ProfilePictureService profilePictureService;

    public UserController(UserService userService, ProfilePictureService profilePictureService) {
        this.userService = userService;
        this.profilePictureService = profilePictureService;
    }

    @GetMapping("/show/{id}")
    public String showUser(Model model, @PathVariable long id){
        User user = userService.findById(id).orElseThrow();
        model.addAttribute(USER_ATTRIBUTE, user);
        return "user/show";
    }

    @GetMapping("/details/{id}")
    public String userDetails(Model model, @PathVariable long id){
        User user = userService.findById(id).orElseThrow();
        model.addAttribute(USER_ATTRIBUTE, user);
        model.addAttribute("dirName", profilePictureService.getDirectory());
        return "user/details";
    }
}
