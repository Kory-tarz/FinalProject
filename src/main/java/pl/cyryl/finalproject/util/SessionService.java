package pl.cyryl.finalproject.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.app.offer.Offer;
import pl.cyryl.finalproject.exceptions.UserIdNotFoundException;
import pl.cyryl.finalproject.users.user.User;
import pl.cyryl.finalproject.users.user.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Service
public class SessionService {

    private static final String USER_ID = "userId";
    private static final String OFFER_ATTRIBUTE = "offer";

    private final UserService userService;

    public SessionService(UserService userService) {
        this.userService = userService;
    }

    public long getCurrentUserId(HttpSession session){
        Long id = (Long)session.getAttribute(USER_ID);
        if(id == null){
            id = getIdFromPrincipal();
            session.setAttribute(USER_ID, id);
        }
        return id;
    }

    public Offer getCurrentOffer(HttpSession session){
        return (Offer)session.getAttribute(OFFER_ATTRIBUTE);
    }

    public void saveCurrentOffer(HttpSession session, Offer currentOffer){
        session.setAttribute(OFFER_ATTRIBUTE, currentOffer);
    }

    public void clearOffer(HttpSession session) {
        session.setAttribute(OFFER_ATTRIBUTE, null);
    }

    private long getIdFromPrincipal(){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<User> currentUser = Optional.empty();
        if(principal instanceof UserDetails){
            currentUser = userService.findByEmail(((UserDetails) principal).getUsername());
        }

        if(currentUser.isPresent()){
            return currentUser.get().getId();
        } else {
            throw new UserIdNotFoundException(USER_ID + " is null");
        }
    }
}
