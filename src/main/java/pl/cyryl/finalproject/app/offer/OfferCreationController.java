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
import pl.cyryl.finalproject.util.SessionService;

import javax.servlet.http.HttpSession;

@RequestMapping("/offer")
@Controller
public class OfferCreationController {

    private final ItemService itemService;
    private final UserService userService;
    private final OfferService offerService;
    private final SessionService sessionService;

    public OfferCreationController(ItemService itemService, UserService userService, OfferService offerService, SessionService sessionService) {
        this.itemService = itemService;
        this.userService = userService;
        this.offerService = offerService;
        this.sessionService = sessionService;
    }

    @GetMapping("/create/{id}")
    public String submitOffer(HttpSession session, @PathVariable long id) {
        Item item = itemService.findItem(id).orElseThrow();
        Offer offer = new Offer();
        offer.getOfferedItems().add(item);
        offer.setReceivingUser(item.getOwner());
        offer.setSubmittingUser(userService.findById(sessionService.getCurrentUserId(session)).orElseThrow());
        sessionService.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @GetMapping("/add/{id}")
    public String addYourItem(HttpSession session, @PathVariable long id) {
        Item item = itemService.findItem(id).orElseThrow();
        Offer offer = sessionService.getCurrentOffer(session);
        if (sessionService.getCurrentUserId(session) != item.getOwner().getId() || offer == null) {
            // it's not your item or you have no active offer - you shouldn't be here
            return "redirect:/";
        }
        offer.getSubmittedItems().add(item);
        sessionService.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @GetMapping("/add/{item_id}/{userId}")
    public String addHisItem(HttpSession session, @PathVariable long item_id, @PathVariable long userId) {
        Item item = itemService.findPublicItem(item_id).orElseThrow();
        Offer offer = sessionService.getCurrentOffer(session);
        if (sessionService.getCurrentUserId(session) == item.getOwner().getId()
                || offer == null
                || offer.getReceivingUser().getId() != userId) {
            return "redirect:/";
        }
        offer.getOfferedItems().add(item);
        sessionService.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @RequestMapping("/show")
    public String showCurrentOffer(HttpSession session, Model model){
        Offer currentOffer = sessionService.getCurrentOffer(session);
        if(currentOffer == null){
            model.addAttribute("error_msg", "Nie masz aktywnej oferty");
        }
        return "offer/show";
    }

    @PostMapping("/submit")
    public String submitCurrentOffer(HttpSession session, Model model){
        Offer currentOffer = sessionService.getCurrentOffer(session);
        if(!offerService.isOfferValid(currentOffer)){
            // TODO decide what happens here
            model.addAttribute("error_msg", "Oferta jest nieprawidłowa");
            return "offer/show";
        }
        if(offerService.hasOfferChanged(currentOffer)){
            currentOffer = offerService.refreshOffer(currentOffer);
            sessionService.saveCurrentOffer(session, currentOffer);
            model.addAttribute("error_msg", "Przedmioty w ofercie zostały edytowane");
            return "offer/show";
        }
        offerService.submitOffer(currentOffer);
        sessionService.clearOffer(session);
        return "redirect:/offer/list/submitted";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItem(HttpSession session, @PathVariable long itemId){
        Offer currentOffer = sessionService.getCurrentOffer(session);
        if(currentOffer == null){
            return "redirect:/";
        }
        currentOffer.removeItem(itemId);
        sessionService.saveCurrentOffer(session, currentOffer);
        return "offer/show";
    }

    @PostMapping("/cancel")
    public String cancelOffer(HttpSession session){
        sessionService.clearOffer(session);
        return "redirect:/";
    }
}
