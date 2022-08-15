package pl.cyryl.finalproject.app.offer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cyryl.finalproject.app.item.ItemService;
import pl.cyryl.finalproject.users.user.UserService;
import pl.cyryl.finalproject.util.SessionService;

import javax.servlet.http.HttpSession;

@RequestMapping("/offer")
@Controller
public class OfferController {

    private final UserService userService;
    private final ItemService itemService;
    private final OfferService offerService;
    private final SessionService sessionService;

    private final String OFFERS_LIST = "offers";

    public OfferController(UserService userService, ItemService itemService, OfferService offerService, SessionService sessionService) {
        this.userService = userService;
        this.itemService = itemService;
        this.offerService = offerService;
        this.sessionService = sessionService;
    }

    @RequestMapping("/list/submitted")
    public String listOffers(HttpSession session, Model model){
        long userId = sessionService.getCurrentUserId(session);
        model.addAttribute(OFFERS_LIST, offerService.findSubmittedOffersByUser(userId));
        return "offer/submitted";
    }

    @RequestMapping("/list")
    public String redirectList(){
        return "redirect:/offer/list/received";
    }


    @RequestMapping("/list/received")
    public String listReceivedOffers(HttpSession session, Model model){
        long userId = sessionService.getCurrentUserId(session);
        model.addAttribute(OFFERS_LIST, offerService.findReceivedOffersByUser(userId));
        return "offer/received";
    }

    @RequestMapping("/list/accepted")
    public String listAcceptedOffers(HttpSession session, Model model){
        long userId = sessionService.getCurrentUserId(session);
        model.addAttribute(OFFERS_LIST, offerService.findAcceptedOffersByUser(userId));
        return "offer/accepted";
    }

    @RequestMapping("/withdraw")
    public String withdrawFromOffer(Offer offer){
        offerService.withdrawOffer(offer.getId());
        return "redirect:/offer/list/accepted";
    }

    @GetMapping("details/{offerId}")
    public String showOfferDetails(HttpSession session, Model model, @PathVariable long offerId){
        long userId = sessionService.getCurrentUserId(session);
        Offer offer = offerService.findOfferBelongingToUser(offerId, userId).orElseThrow();
        //TODO should we be able to see offers between other people?
        model.addAttribute(offer);
        return "offer/details";
    }

    @GetMapping("accepted_details/{offerId}")
    public String showAcceptedOfferDetails(HttpSession session, Model model, @PathVariable long offerId){
        long userId = sessionService.getCurrentUserId(session);
        Offer offer = offerService.findOfferBelongingToUser(offerId, userId).orElseThrow();
        //TODO should we be able to see offers between other people?
        model.addAttribute(offer);
        return "offer/accepted_details";
    }

    @PostMapping("/edit")
    public String editYourOffer(HttpSession session, Offer offer){
        sessionService.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @PostMapping("/negotiate")
    public String negotiateOffer(HttpSession session, Offer offer){
        Offer counterOffer = offer.createCounterOffer();
        sessionService.saveCurrentOffer(session, counterOffer);
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
