package pl.cyryl.finalproject.users.user;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.ResolvableType;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePicture;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePictureService;
import pl.cyryl.finalproject.users.user.exception.EmailAlreadyRegisteredException;
import pl.cyryl.finalproject.users.user.exception.UserNotFoundException;
import pl.cyryl.finalproject.users.user.verification.VerificationToken;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserAuthController {

    private final UserService userService;
    private final ProfilePictureService profilePictureService;
    private final ApplicationEventPublisher eventPublisher;
    private final ClientRegistrationRepository clientRegistrationRepository;

    private final String authorizationRequestBaseUri = "oauth2/authorization";
    private final String USER_ATTRIBUTE = "user";
    private final String PROFILE_PICTURES = "profile_pictures";

    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();

    public UserAuthController(UserService userService, ProfilePictureService profilePictureService, ApplicationEventPublisher eventPublisher, ClientRegistrationRepository clientRegistrationRepository) {
        this.userService = userService;
        this.profilePictureService = profilePictureService;
        this.eventPublisher = eventPublisher;
        this.clientRegistrationRepository = clientRegistrationRepository;
    }


    @GetMapping("/register")
    public String registerUser(Model model) {
        List<ProfilePicture> publicPictures = profilePictureService.findPublicProfilePictures();
        model.addAttribute(PROFILE_PICTURES, publicPictures);
        model.addAttribute("pictureDir", profilePictureService.getDirectory());
        model.addAttribute(USER_ATTRIBUTE, new User());
        return "user/register";
    }

    @PostMapping("/register")
    public String addNewUser(Model model, HttpServletRequest request, @Valid User user, BindingResult result) {
        if (result.hasErrors()) {
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
        return "redirect:/user/show/" + user.getId();
    }

    @GetMapping("/registration/confirm")
    public String confirmRegistration(Model model, @RequestParam("token") String token) {
        Optional<VerificationToken> verificationToken = userService.getVerificationToken(token);
        if (verificationToken.isEmpty()) {
            String message = "Invalid token";
            model.addAttribute("error_msg", message);
            return "redirect:/";
        }
        if (!verificationToken.get().isActive()) {
            String message = "Token expired";
            model.addAttribute("error_msg", message);
            return "redirect:/";
        }
        User user = verificationToken.get().getUser();
        user.setEnabled(true);
        userService.saveRegisteredUser(user);
        return "redirect:/";
    }

    @SuppressWarnings("unchecked")
    @GetMapping("/login")
    public String login(Model model) {
        Iterable<ClientRegistration> clientRegistrations = null;
        ResolvableType type = ResolvableType.forInstance(clientRegistrationRepository)
                .as(Iterable.class);
        if (type != ResolvableType.NONE && ClientRegistration.class.isAssignableFrom(type.resolveGenerics()[0])) {
            clientRegistrations = (Iterable<ClientRegistration>) clientRegistrationRepository;
        }
        clientRegistrations.forEach(registration ->
                oauth2AuthenticationUrls.put(registration.getClientName(),
                        authorizationRequestBaseUri + "/" + registration.getRegistrationId()));
        model.addAttribute("urls", oauth2AuthenticationUrls);
        return "/login";
    }

//    @GetMapping("/login")
//    @ExceptionHandler(UserNotFoundException.class)
//    public String userNotFound(Model model, Exception exception) {
//        model.addAttribute("error_msg", exception.getMessage());
//        return "/login";
//    }
}
