package pl.cyryl.finalproject.users.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePictureService;
import pl.cyryl.finalproject.util.SessionService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.function.BinaryOperator;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    private final ProfilePictureService profilePictureService;
    private final SessionService sessionService;

    private final String USER_ATTRIBUTE = "user";
    private final String PROFILE_PICTURES = "profile_pictures";

    public UserController(UserService userService, ProfilePictureService profilePictureService, SessionService sessionService) {
        this.userService = userService;
        this.profilePictureService = profilePictureService;
        this.sessionService = sessionService;
    }

    @GetMapping("/details/{id}")
    public String userDetails(Model model, @PathVariable long id){
        User user = userService.findById(id).orElseThrow();
        model.addAttribute(USER_ATTRIBUTE, user);
        model.addAttribute("dirName", profilePictureService.getDirectory());
        return "user/details";
    }

    @GetMapping("/edit/{id}")
    public String editUser(Model model, HttpSession session, @PathVariable long id){
        if (id != sessionService.getCurrentUserId(session)){
            return "redirect:/";
        }
        setEditUserAttributes(model, userService.findById(id).orElseThrow());
        return "user/edit";
    }

    @PostMapping("/edit/{id}")
    public String saveChanges(Model model, @Valid User user, BindingResult result, @PathVariable long id){
        if (result.hasErrors()){
            setEditUserAttributes(model, user);
            return "user/edit";
        }
        userService.saveRegisteredUser(user);
        return "redirect:/user/details/" + id;
    }

    private void setEditUserAttributes(Model model, User user){
        model.addAttribute(USER_ATTRIBUTE, user);
        model.addAttribute(PROFILE_PICTURES, profilePictureService.findPublicProfilePictures());
        model.addAttribute("pictureDir", profilePictureService.getDirectory());
    }
}
