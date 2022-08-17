package pl.cyryl.finalproject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePicture;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePictureRepository;
import pl.cyryl.finalproject.util.FilesService;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class HomepageController {


    private final ProfilePictureRepository profilePictureRepository;
    private final FilesService filesService;

    public HomepageController(ProfilePictureRepository profilePictureRepository, FilesService filesService) {
        this.profilePictureRepository = profilePictureRepository;
        this.filesService = filesService;
    }

    @GetMapping("/")
    public String start(){
        return "/index";
    }

    @GetMapping("/other")
    @ResponseBody
    public String otherUser(HttpSession session){
        session.setAttribute("userId", 2L);
        return "2";
    }

    @GetMapping("/addp")
    public String addProfile(){
        return "/add";
    }

    @PostMapping("/addp")
    public String saveProfile(@RequestParam("image") MultipartFile multipartFile, Model model) throws IOException {
        ProfilePicture profilePicture = filesService.getPhotoWithPath(new ProfilePicture(), multipartFile);
        profilePicture.setPublicPicture(true);
        profilePicture = profilePictureRepository.save(profilePicture);
        filesService.saveProfilePicture(profilePicture , multipartFile);

        model.addAttribute("photo", profilePicture);
        model.addAttribute("photoDir", filesService.getProfilePicturesDirectory());
        return "/item/display";
    }

    @RequestMapping("/notfound")
    public String error(HttpServletResponse response){
        response.setStatus(404);
        return "/notfound";
    }

    @RequestMapping("/403")
    public String accessDenied(HttpServletResponse response){
        response.setStatus(403);
        return "/denied";
    }

}
