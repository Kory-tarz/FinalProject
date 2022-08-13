package pl.cyryl.finalproject.users.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePicture;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePictureRepository;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePictureService;
import pl.cyryl.finalproject.users.user.exception.EmailAlreadyRegisteredException;
import pl.cyryl.finalproject.util.FilesUtil;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/user")
@Controller
public class UserController {

    private final UserService userService;
    private final String USER_ATTRIBUTE = "user";
    private final String PROFILE_PICTURES = "profile_pictures";
    private final ProfilePictureService profilePictureService;

    public UserController(UserService userService, ProfilePictureService profilePictureService) {
        this.userService = userService;
        this.profilePictureService = profilePictureService;
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
    public String addNewUser(Model model, @Valid User user, BindingResult result){
        if(result.hasErrors()){
            model.addAttribute(USER_ATTRIBUTE, user);
            return "user/register";
        }
        try {
            userService.registerNewUser(user);
        } catch (EmailAlreadyRegisteredException e) {
            model.addAttribute("error_msg", e.getMessage());
            return "user/register";
        }
        return "redirect:show/" + user.getId();
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
