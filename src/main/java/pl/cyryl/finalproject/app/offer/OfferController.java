package pl.cyryl.finalproject.app.offer;

import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.item.ItemService;
import pl.cyryl.finalproject.users.user.UserService;
import pl.cyryl.finalproject.util.SessionUtils;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequestMapping("/offer")
@Controller
public class OfferController {

    private final UserService userService;
    private final ItemService itemService;

    public OfferController(UserService userService, ItemService itemService) {
        this.userService = userService;
        this.itemService = itemService;
    }

    @GetMapping("/create/{id}")
    public String submitOffer(HttpSession session, @PathVariable long id) {
        System.out.println("Creating");
        Item item = itemService.findItem(id).orElseThrow();
        Set<Item> items = new HashSet<>(List.of(item));
        Offer offer = new Offer();
        offer.setOfferedItems(items);
        offer.setReceivingUser(item.getOwner());
        offer.setSubmittingUser(userService.findById(SessionUtils.getCurrentUserId(session)).orElseThrow());
        SessionUtils.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @GetMapping("/add/{id}")
    public String addYourItem(HttpSession session, @PathVariable long id) {
        Item item = itemService.findItem(id).orElseThrow();
        Offer offer = SessionUtils.getCurrentOffer(session);
        if (SessionUtils.getCurrentUserId(session) != item.getOwner().getId() || offer == null) {
            // it's not your item or you have no active offer - you shouldn't be here
            return "redirect:/";
        }
        offer.getSubmittedItems().add(item);
        SessionUtils.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @GetMapping("/add/{item_id}/{user_id}")
    public String addHisItem(HttpSession session, @PathVariable long item_id, @PathVariable long user_id) {
        Item item = itemService.findPublicItem(item_id).orElseThrow();
        Offer offer = SessionUtils.getCurrentOffer(session);
        if (SessionUtils.getCurrentUserId(session) == item.getOwner().getId()
                || offer == null
                || offer.getReceivingUser().getId() != user_id) {
            // you can't trade your own item or trade with multiple people
            return "redirect:/";
        }
        offer.getOfferedItems().add(item);
        SessionUtils.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @RequestMapping("/show")
    public String showCurrentOffer(HttpSession session){
        Offer currentOffer = SessionUtils.getCurrentOffer(session);
        if(currentOffer == null){
            return "redirect:/";
        }
        return "offer/show";
    }
}
