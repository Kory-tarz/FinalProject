package pl.cyryl.finalproject;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.app.photo.Photo;
import pl.cyryl.finalproject.app.photo.PhotoRepository;
import pl.cyryl.finalproject.util.FileUploadUtil;

import java.io.IOException;

@Controller
public class TestController {

    private final PhotoRepository photoRepository;

    public TestController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
    }

    @GetMapping("/")
    @ResponseBody
    public String start(){
        return "all good";
    }

    @GetMapping("/admin/")
    @ResponseBody
    public String start2(){
        return "all admin";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/add")
    public String addPhoto(){
        return "/item/add";
    }

    @PostMapping("/add")
    public String savePhoto(@RequestParam("image") MultipartFile multipartFile) throws IOException {
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        Photo photo = new Photo();
        photo.setPath(fileName);

        photo = photoRepository.save(photo);
        String uploadDir = "user-photos/" + photo.getId();
        FileUploadUtil.saveFile(uploadDir, fileName, multipartFile);
        System.out.println("Hi there");
        return "redirect:/display";
    }

    @GetMapping("/display")
    public String showPhoto(){
        return "/item/display";
    }

}
