package pl.cyryl.finalproject.app.offer;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.item.ItemService;
import pl.cyryl.finalproject.app.offer.status.Status;
import pl.cyryl.finalproject.app.offer.status.StatusData;
import pl.cyryl.finalproject.app.offer.status.StatusService;
import pl.cyryl.finalproject.app.offer.validation.OfferValidationResult;
import pl.cyryl.finalproject.app.offer.validation.OfferValidator;
import pl.cyryl.finalproject.exceptions.UnauthorizedAccessException;
import pl.cyryl.finalproject.util.EntityActivationService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static pl.cyryl.finalproject.app.offer.validation.OfferValidator.*;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final StatusService statusService;
    private final EntityActivationService entityActivationService;
    private final ItemService itemService;

    public OfferService(OfferRepository offerRepository, StatusService statusService, EntityActivationService entityActivationService, ItemService itemService) {
        this.offerRepository = offerRepository;
        this.statusService = statusService;
        this.entityActivationService = entityActivationService;
        this.itemService = itemService;
    }

    public boolean isOfferValid(Offer offer) {

        OfferValidator validator = isBetweenDifferentUsers()
                .and(hasItems())
                .and(hasActiveItems())
                .and(itemsBelongToSubmittingUser())
                .and(itemsBelongToReceivingUser());

        OfferValidationResult result = validator.apply(offer);
        if (result.equals(OfferValidationResult.SUCCESS)) {
            return true;
        } else {
            System.out.println(result);
            // TODO log error
            return false;
        }
    }

    public boolean hasOfferChanged(Offer offer) {
        return offer.getOfferedItems().stream().anyMatch(itemService::hasItemChanged)
                || offer.getSubmittedItems().stream().anyMatch(itemService::hasItemChanged);
    }

    public Offer refreshOffer(Offer offer) {
        offer.setSubmittedItems(getRefreshedItems(offer.getSubmittedItems()));
        offer.setOfferedItems(getRefreshedItems(offer.getOfferedItems()));
        return offer;
    }

    private Set<Item> getRefreshedItems(Set<Item> items) {
        return items.stream().map(item -> itemService.findItem(item.getId()).orElseThrow()).collect(Collectors.toSet());
    }

    public void submitOffer(Offer offer) {
        if (offer.isNew()) {
            submitNewOffer(offer);
        } else {
            submitNextOfferVersion(offer);
        }
    }

    private void submitNextOfferVersion(Offer offer) {
        Offer prevOffer = offerRepository.findById(offer.getId()).orElseThrow();
        if (!prevOffer.getStatus().equals(statusService.getSubmittedStatus())) {
            throw new UnauthorizedAccessException("Attempt to modify offer with id: " + offer.getId());
        }
        offer.resetId();
        offer.setPreviousVersion(prevOffer);
        offer = submitNewOffer(offer);
        prevOffer.setNextVersion(offer);
        prevOffer.setStatus(statusService.getHistoryStatus());
        offerRepository.save(prevOffer);
    }

    private Offer submitNewOffer(Offer offer) {
        Status status = statusService.getSubmittedStatus();
        offer.setStatus(status);
        return offerRepository.save(offer);
    }

    public List<Offer> findSubmittedOffersByUser(long id) {
        Status status = statusService.getSubmittedStatus();
        return offerRepository.findAllBySubmittingUserIdAndStatus(id, status);
    }

    public List<Offer> findReceivedOffersByUser(long id) {
        Status status = statusService.getSubmittedStatus();
        return offerRepository.findAllByReceivingUserIdAndStatus(id, status);
    }

    public List<Offer> findAcceptedOffersByUser(long id) {
        List<Status> allAcceptedStatuses = statusService.getAllAcceptedStatuses();
        return offerRepository.findAllByUserWithStatuses(id, allAcceptedStatuses);
    }

    public List<Offer> findCompletedOffersByUser(long id){
        Status status = statusService.getCompletedStatus();
        return offerRepository.findAllOffersWithStatus(id, status);
    }

    public Optional<Offer> findOffer(long id) {
        return offerRepository.findById(id);
    }

    public Optional<Offer> findOfferBelongingToUser(long offerId, long userId) {
        Optional<Offer> offerOpt = offerRepository.findById(offerId);
        if (offerOpt.isPresent()) {
            Offer offer = offerOpt.get();
            if (offer.getSubmittingUser().getId() == userId || offer.getReceivingUser().getId() == userId) {
                return offerOpt;
            }
        }
        return Optional.empty();
    }

    public void acceptOffer(Offer offer) {
        Status status = statusService.getAcceptedStatus();
        offer.setStatus(status);
        offer = offerRepository.save(offer);
        entityActivationService.deactivateOfferItems(offer);
    }

    public void withdrawOffer(long offerId) {
        Offer offerInDb = offerRepository.findById(offerId).orElseThrow();
        Status acceptedStatus = statusService.getAcceptedStatus();
        if (offerInDb.getStatus().equals(acceptedStatus)) {
            Status canceledStatus = statusService.getCanceledStatus();
            offerInDb.setStatus(canceledStatus);
            entityActivationService.activateItemsInOffer(offerInDb);
            offerRepository.save(offerInDb);
        }
    }

    public Offer findOfferWithHistory(long offerId) {
        Offer offer = offerRepository.findById(offerId).orElseThrow();
        Hibernate.initialize(offer.getPreviousVersion());
        Hibernate.initialize(offer.getNextVersion());
        return offer;
    }

    public void confirmReceivingItem(Offer offer, long userConfirming) {
        Status acceptedStatus = statusService.getAcceptedStatus();
        if (offer.getStatus().equals(acceptedStatus)) {
            oneItemReceived(offer, userConfirming);
        } else {
            finalizeOffer(offer, userConfirming);
        }
    }

    private void finalizeOffer(Offer offer, long userConfirming) {
        Status currentStatus = offer.getStatus();
        Status receivingUser = statusService.getConfirmedByReceivingUserStatus();
        if (currentStatus.equals(receivingUser) && offer.getReceivingUser().getId() == userConfirming) {
            // Somehow we received second confirmation from the seme user
            return;
        }
        Status completedStatus = statusService.getCompletedStatus();
        offer.setStatus(completedStatus);
        offerRepository.save(offer);
    }

    private void oneItemReceived(Offer offer, long userConfirming) {
        Status status;
        if (userConfirming == offer.getReceivingUser().getId()) {
            status = statusService.getConfirmedByReceivingUserStatus();
        } else {
            status = statusService.getConfirmedBySubmittingUserStatus();
        }
        offer.setStatus(status);
        offerRepository.save(offer);
    }

    public StatusData getStatusDataFromOffer(Offer offer, long userId){
        StatusData statusData = new StatusData();
        if(offer.getSubmittingUser().getId() != userId && offer.getReceivingUser().getId() != userId){
            statusData.setMyOffer(false);
            return statusData;
        }
        Status offerStatus = offer.getStatus();
        if(offerStatus.getName().equals(statusService.CONFIRMED_RECEIVING)){
            statusData.setReceivedByMe(offer.getReceivingUser().getId() == userId);
            statusData.setReceivedByOther(!statusData.isReceivedByMe());
        }
        if(offerStatus.getName().equals(statusService.CONFIRMED_SUBMITTING)){
            statusData.setReceivedByMe(offer.getSubmittingUser().getId() == userId);
            statusData.setReceivedByOther(!statusData.isReceivedByMe());
        }
        if(offerStatus.getName().equals(statusService.COMPLETED)){
            statusData.setReceivedByMe(true);
            statusData.setReceivedByOther(true);
        }
        return statusData;
    }
}
