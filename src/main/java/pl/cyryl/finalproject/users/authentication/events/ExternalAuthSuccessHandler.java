package pl.cyryl.finalproject.users.authentication.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import pl.cyryl.finalproject.users.authentication.userData.CustomOAuth2User;
import pl.cyryl.finalproject.users.user.User;
import pl.cyryl.finalproject.users.user.UserService;
import pl.cyryl.finalproject.util.SessionService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExternalAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private SessionService sessionService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
        User user = userService.processOAuthLogin(new User(oauthUser));
        sessionService.saveUserId(request.getSession(), user.getId());
        response.sendRedirect("/user/panel");
    }
}
