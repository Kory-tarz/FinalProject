package pl.cyryl.finalproject.app.offer;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.item.ItemService;
import pl.cyryl.finalproject.users.user.UserService;
import pl.cyryl.finalproject.util.SessionUtils;

import javax.servlet.http.HttpSession;

@RequestMapping("/offer")
@Controller
public class OfferCreationController {

    private final ItemService itemService;
    private final UserService userService;
    private final OfferService offerService;

    public OfferCreationController(ItemService itemService, UserService userService, OfferService offerService) {
        this.itemService = itemService;
        this.userService = userService;
        this.offerService = offerService;
    }

    @GetMapping("/create/{id}")
    public String submitOffer(HttpSession session, @PathVariable long id) {
        Item item = itemService.findItem(id).orElseThrow();
        Offer offer = new Offer();
        offer.getOfferedItems().add(item);
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

    @PostMapping("/submit")
    public String submitCurrentOffer(HttpSession session, Model model){
        Offer currentOffer = SessionUtils.getCurrentOffer(session);
        if(!offerService.isOfferValid(currentOffer)){
            // TODO
            model.addAttribute("error_msg", "invalid Offer");
            return "offer/show";
        }
        currentOffer = offerService.submitOffer(currentOffer);
        SessionUtils.clearOffer(session);
        return "redirect:/offer/list/submitted";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItem(HttpSession session, @PathVariable long itemId){
        Offer currentOffer = SessionUtils.getCurrentOffer(session);
        if(currentOffer == null){
            return "redirect:/";
        }
        currentOffer.removeItem(itemId);
        SessionUtils.saveCurrentOffer(session, currentOffer);
        return "offer/show";
    }

    @PostMapping("/cancel")
    public String cancelOffer(HttpSession session){
        SessionUtils.clearOffer(session);
        return "redirect:/";
    }
}
