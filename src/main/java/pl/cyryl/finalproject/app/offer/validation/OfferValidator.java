package pl.cyryl.finalproject.app.offer.validation;

import pl.cyryl.finalproject.app.offer.Offer;

import java.util.function.Function;

import static pl.cyryl.finalproject.app.offer.validation.OfferValidationResult.*;

@FunctionalInterface
public interface OfferValidator extends Function<Offer, OfferValidationResult> {

    static OfferValidator isBetweenDifferentUsers() {
        return offer -> offer.getSubmittingUser().getId() == offer.getReceivingUser().getId()
                ? THE_SAME_USERS : SUCCESS ;
    }

    static OfferValidator hasItems() {
        return offer -> (offer.getSubmittedItems().size() == 0
                || offer.getOfferedItems().size() == 0)
                ? USER_DOESNT_HAVE_ITEMS : SUCCESS;
    }

    static OfferValidator hasActiveItems() {
        return offer -> (offer.getOfferedItems().stream().anyMatch(item -> !item.isActive()) ||
                offer.getSubmittedItems().stream().anyMatch(item -> !item.isActive()))
                ? INACTIVE_ITEM_IN_OFFER : SUCCESS;
    }

    static OfferValidator itemsBelongToSubmittingUser() {
        return offer -> offer.getOfferedItems().stream()
                .allMatch(item -> item.getOwner().getId() == offer.getReceivingUser().getId())
                ? SUCCESS : ITEM_DOESNT_BELONG_TO_SUBMITTING_USER;
    }

    static OfferValidator itemsBelongToReceivingUser(){
        return offer -> offer.getSubmittedItems().stream()
                .allMatch(item -> item.getOwner().getId() == offer.getSubmittingUser().getId())
                ? SUCCESS : ITEM_DOESNT_BELONG_TO_RECEIVING_USER;
    }

    default OfferValidator and (OfferValidator other){
        return offer -> {
            OfferValidationResult result = this.apply(offer);
            return result.equals(SUCCESS) ? other.apply(offer) : result;
        };
    }
}
