package pl.cyryl.finalproject.app.offer;

import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.app.offer.status.Status;
import pl.cyryl.finalproject.app.offer.status.StatusService;

import java.util.List;
import java.util.Optional;

@Service
public class OfferService {

    private final OfferRepository offerRepository;
    private final StatusService statusService;

    public OfferService(OfferRepository offerRepository, StatusService statusService) {
        this.offerRepository = offerRepository;
        this.statusService = statusService;
    }

    public boolean isOfferValid(Offer offer) {
        long receivingId = offer.getReceivingUser().getId();
        long submittingId = offer.getSubmittingUser().getId();

        // users have to be different
        if (receivingId == submittingId) {
            return false;
        }
        // both users must have items
        if (offer.getSubmittedItems().size() == 0 || offer.getOfferedItems().size() == 0) {
            return false;
        }
        // inactive item
        if (offer.getOfferedItems().stream().anyMatch(item -> !item.isActive())) {
            return false;
        }
        if (offer.getSubmittedItems().stream().anyMatch(item -> !item.isActive())) {
            return false;
        }
        // all items offered belong to receiving user
        if (!(offer.getOfferedItems().stream().allMatch(item -> item.getOwner().getId() == receivingId))) {
            return false;
        }
        // all items submitted belong to submitting user
        if (!(offer.getSubmittedItems().stream().allMatch(item -> item.getOwner().getId() == submittingId))) {
            return false;
        }
        return true;
    }

    public Offer submitOffer(Offer offer) {
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

    public Optional<Offer> findOffer(long id){
        return offerRepository.findById(id);
    }

    public Optional<Offer> findOfferBelongingToUser(long offerId, long userId){
        Optional<Offer> offerOpt = offerRepository.findById(offerId);
        if(offerOpt.isPresent()){
            Offer offer = offerOpt.get();
            if(offer.getSubmittingUser().getId() == userId || offer.getReceivingUser().getId() == userId){
                return offerOpt;
            }
        }
        return Optional.empty();
    }

}
