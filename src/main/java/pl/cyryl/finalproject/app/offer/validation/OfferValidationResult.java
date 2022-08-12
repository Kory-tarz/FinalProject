package pl.cyryl.finalproject.app.offer.validation;

public enum OfferValidationResult {
    THE_SAME_USERS,
    USER_DOESNT_HAVE_ITEMS,
    INACTIVE_ITEM_IN_OFFER,
    ITEM_DOESNT_BELONG_TO_RECEIVING_USER,
    ITEM_DOESNT_BELONG_TO_SUBMITTING_USER,
    SUCCESS
}
