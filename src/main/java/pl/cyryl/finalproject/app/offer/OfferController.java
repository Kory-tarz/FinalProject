package pl.cyryl.finalproject.app.offer;

import org.springframework.security.access.method.P;
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
public class OfferController {

    private final UserService userService;
    private final ItemService itemService;
    private final OfferService offerService;

    private final String OFFERS_LIST = "offers";
    private final String OFFER_ATTRIBUTE = "offer";

    public OfferController(UserService userService, ItemService itemService, OfferService offerService) {
        this.userService = userService;
        this.itemService = itemService;
        this.offerService = offerService;
    }

    @RequestMapping("/list/submitted")
    public String listOffers(HttpSession session, Model model){
        long userId = SessionUtils.getCurrentUserId(session);
        model.addAttribute(OFFERS_LIST, offerService.findSubmittedOffersByUser(userId));
        return "offer/submitted";
    }

    @RequestMapping("/list")
    public String redirectList(){
        return "redirect:/offer/list/received";
    }

    @RequestMapping("/list/received")
    public String listReceivedOffers(HttpSession session, Model model){
        long userId = SessionUtils.getCurrentUserId(session);
        model.addAttribute(OFFERS_LIST, offerService.findReceivedOffersByUser(userId));
        return "offer/received";
    }

    @GetMapping("details/{offerId}")
    public String showOfferDetails(HttpSession session, Model model, @PathVariable long offerId){
        long userId = SessionUtils.getCurrentUserId(session);
        Offer offer = offerService.findOfferBelongingToUser(offerId, userId).orElseThrow();
        //TODO should we be able to see offers between other people?
        model.addAttribute(model.addAttribute(offer));
        return "offer/details";
    }

    @PostMapping("/edit")
    public String editYourOffer(HttpSession session, Offer offer){
        SessionUtils.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @PostMapping("/negotiate")
    public String negotiateOffer(HttpSession session, Offer offer){
        Offer counterOffer = offer.createCounterOffer();
        SessionUtils.saveCurrentOffer(session, counterOffer);
        return "offer/show";
    }

    @PostMapping("/accept")
    public String acceptOffer(Offer offer){
        if(offerService.isOfferValid(offer)){
            offerService.acceptOffer(offer);
            return "offer/accepted";
        }
        return "redirect:/";
    }
}
