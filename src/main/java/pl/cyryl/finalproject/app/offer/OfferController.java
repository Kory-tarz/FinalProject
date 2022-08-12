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
        return "offer/accepted";
    }

    @GetMapping("/remove/{id}")
    public String removeItem(HttpSession session, @PathVariable long itemId){
        Offer currentOffer = SessionUtils.getCurrentOffer(session);
        if(currentOffer == null){
            return "redirect:/";
        }
        currentOffer.removeItem(itemId);
        SessionUtils.saveCurrentOffer(session, currentOffer);
        return "offer/show";
    }
}
