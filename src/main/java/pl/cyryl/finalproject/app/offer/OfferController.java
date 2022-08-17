package pl.cyryl.finalproject.app.offer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cyryl.finalproject.exceptions.UnauthorizedAccessException;
import pl.cyryl.finalproject.util.EntityActivationService;
import pl.cyryl.finalproject.util.SessionService;

import javax.servlet.http.HttpSession;
import java.util.List;

@RequestMapping("/offer")
@Controller
public class OfferController {

    private final OfferService offerService;
    private final SessionService sessionService;

    private final String OFFERS_LIST = "offers";
    private final String OFFER_ATTRIBUTE = "offer";
    private final String STATUS_DATA = "status";

    public OfferController(OfferService offerService, SessionService sessionService) {
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

    @RequestMapping("/list/completed")
    public String listCompletedOffers(HttpSession session, Model model){
        long userId = sessionService.getCurrentUserId(session);
        model.addAttribute(OFFERS_LIST, offerService.findCompletedOffersByUser(userId));
        return "offer/completed";
    }

    @RequestMapping("/withdraw")
    public String withdrawFromOffer(HttpSession session, Offer offer){
        long userId = sessionService.getCurrentUserId(session);
        validateAccess(offer, userId);
        offerService.withdrawOffer(offer.getId());
        return "redirect:/offer/list/accepted";
    }

    @GetMapping("details/{offerId}")
    public String showOfferDetails(HttpSession session, Model model, @PathVariable long offerId){
        long userId = sessionService.getCurrentUserId(session);
        Offer offer = offerService.findOfferBelongingToUser(offerId, userId).orElseThrow();
        model.addAttribute(OFFER_ATTRIBUTE, offer);
        return "offer/details";
    }

    @GetMapping("accepted_details/{offerId}")
    public String showAcceptedOfferDetails(HttpSession session, Model model, @PathVariable long offerId){
        long userId = sessionService.getCurrentUserId(session);
        Offer offer = offerService.findOfferBelongingToUser(offerId, userId).orElseThrow();
        model.addAttribute(OFFER_ATTRIBUTE, offer);
        model.addAttribute(STATUS_DATA, offerService.getStatusDataFromOffer(offer, userId));
        return "offer/accepted_details";
    }

    @PostMapping("/edit")
    public String editYourOffer(HttpSession session, Offer offer){
        sessionService.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @PostMapping("/negotiate")
    public String negotiateOffer(HttpSession session, Offer offer){
        long userId = sessionService.getCurrentUserId(session);
        validateAccess(offer, userId);
        offer.setCounterOffer();
        sessionService.saveCurrentOffer(session, offer);
        return "offer/show";
    }

    @PostMapping("/accept")
    public String acceptOffer(HttpSession session, Offer offer){
        long userId = sessionService.getCurrentUserId(session);
        validateAccess(offer, userId);
        if(offerService.isOfferValid(offer)){
            offerService.acceptOffer(offer);
            return "offer/accepted";
        }
        return "redirect:/";
    }

    @GetMapping("/history/{id}")
    public String showOfferHistory(Model model, @PathVariable long id){
        Offer offer = offerService.findOfferWithHistory(id);
        model.addAttribute(OFFER_ATTRIBUTE, offer);
        return "offer/history";
    }

    @PostMapping("/transaction/confirmation")
    public String confirmTransaction(HttpSession session, Offer offer){
        long userId = sessionService.getCurrentUserId(session);
        validateAccess(offer, userId);
        offerService.confirmReceivingItem(offer, userId);
        return "redirect:/offer/accepted_details/" + offer.getId();
    }

    private void validateAccess(Offer offer, long userId){
        if(offer.getSubmittingUser().getId() != userId && offer.getReceivingUser().getId() != userId){
            throw new UnauthorizedAccessException("User: " + userId + " tried to modify offer with id =" + offer.getId());
        }
    }

}
