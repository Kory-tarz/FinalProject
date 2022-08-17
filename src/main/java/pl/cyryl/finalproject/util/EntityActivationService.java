package pl.cyryl.finalproject.util;

import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.item.ItemRepository;
import pl.cyryl.finalproject.app.offer.Offer;
import pl.cyryl.finalproject.app.offer.OfferRepository;
import pl.cyryl.finalproject.app.offer.status.Status;
import pl.cyryl.finalproject.app.offer.status.StatusService;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntityActivationService {

    private final ItemRepository itemRepository;
    private final OfferRepository offerRepository;
    private final StatusService statusService;

    public EntityActivationService(ItemRepository itemRepository, OfferRepository offerRepository, StatusService statusService) {
        this.itemRepository = itemRepository;
        this.offerRepository = offerRepository;
        this.statusService = statusService;
    }

    public void deactivateOfferItems(Offer offer) {
        List<Offer> offersToDeactivate = findAllOffersSharingItems(offer);
        offer.getOfferedItems().forEach(this::deactivateItem);
        offer.getSubmittedItems().forEach(this::deactivateItem);
        deactivateOffers(offersToDeactivate, "msg");
    }

    public void deactivateOffers(List<Offer> offers, String message){
        Status inactiveStatus = statusService.getInactiveStatus();
        offers.stream()
                .peek(offer -> offer.setStatus(inactiveStatus))
                .forEach(offerRepository::save);
    }

//    private void deactivateItemSet(Set<Item> items) {
//        items.forEach(this::deactivateItem);
//        items.forEach(item -> deactivateOtherOffersWithItem(item, 1L));
//    }

    private void deactivateItem(Item item) {
        item.setActive(false);
        itemRepository.save(item);
    }

    private void deactivateOtherOffersWithItem(Item item, long offerId) {
        Status submittedStatus = statusService.getSubmittedStatus();
        List<Offer> offers = offerRepository.findAllDifferentOffersWithItemAndStatus(offerId, item, submittedStatus);
        deactivateOffers(offers, "");
    }

    public void deactivateOffersWithItem(Item item) {
        int dummyOfferId = 0;
        deactivateOtherOffersWithItem(item, dummyOfferId);
    }

    public void activateItemsInOffer(Offer offer) {
        offer.getOfferedItems().forEach(this::activateItem);
        offer.getSubmittedItems().forEach(this::activateItem);
    }

    private void activateItem(Item item) {
        item.setActive(true);
        itemRepository.save(item);
    }

    public List<Offer> findAllOffersSharingItems(Offer offer){
        List<Long> allIds = offer.getOfferedItems().stream().map(Item::getId).collect(Collectors.toList());
        allIds.addAll(offer.getSubmittedItems().stream().map(Item::getId).collect(Collectors.toList()));
        Status status = statusService.getSubmittedStatus();
        return offerRepository.findAllOffersWithItems(allIds, offer.getId(), status.getId());
    }
}
