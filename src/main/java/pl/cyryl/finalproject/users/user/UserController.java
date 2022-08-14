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
    private final String PROFILE_PICTURES = "profile_pictures";
    private final ProfilePictureService profilePictureService;
    private final ApplicationEventPublisher eventPublisher;

    public UserController(UserService userService, ProfilePictureService profilePictureService, ApplicationEventPublisher eventPublisher) {
        this.userService = userService;
        this.profilePictureService = profilePictureService;
        this.eventPublisher = eventPublisher;
    }

    @GetMapping("/register")
    public String registerUser(Model model){
        List<ProfilePicture> publicPictures = profilePictureService.findPublicProfilePictures();
        model.addAttribute(PROFILE_PICTURES, publicPictures);
        model.addAttribute("pictureDir", profilePictureService.getDirectory());
        model.addAttribute(USER_ATTRIBUTE, new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String addNewUser(Model model, HttpServletRequest request, @Valid User user, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute(USER_ATTRIBUTE, user);
            return "user/register";
        }
        try {
            userService.registerNewUser(user);
            eventPublisher.publishEvent(new OnRegistrationCompleteEvent(user, request.getContextPath()));
        } catch (EmailAlreadyRegisteredException e) {
            model.addAttribute("error_msg", e.getMessage());
            return "user/register";
        }
        return "redirect:show/" + user.getId();
    }

    @GetMapping("/registration/confirm")
    public String confirmRegistration(Model model, @RequestParam("token") String token){
        Optional<VerificationToken> verificationToken = userService.getVerificationToken(token);
        if(verificationToken.isEmpty()){
            String message = "Invalid token";
            model.addAttribute("error_msg", message);
            return "redirect:/";
        }
        if(!verificationToken.get().isActive()){
            String message = "Token expired";
            model.addAttribute("error_msg", message);
            return "redirect:/";
        }
        User user = verificationToken.get().getUser();
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/";
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

    @GetMapping("/login")
    @ExceptionHandler(UserNotFoundException.class)
    public String userNotFound(Model model, Exception exception){
        model.addAttribute("error_msg", exception.getMessage());
        return "/login";
    }
}
