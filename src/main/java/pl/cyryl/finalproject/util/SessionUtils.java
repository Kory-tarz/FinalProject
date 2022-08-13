package pl.cyryl.finalproject.util;

import pl.cyryl.finalproject.app.offer.Offer;

import javax.servlet.http.HttpSession;

public class SessionUtils {

    private static final String USER_ID = "userId";
    private static final String OFFER_ATTRIBUTE = "offer";

    //TODO throw exception when null?
    public static long getCurrentUserId(HttpSession session){
        return (long)session.getAttribute(USER_ID);
    }

    public static Offer getCurrentOffer(HttpSession session){
        return (Offer)session.getAttribute(OFFER_ATTRIBUTE);
    }

    public static void saveCurrentOffer(HttpSession session, Offer currentOffer){
        session.setAttribute(OFFER_ATTRIBUTE, currentOffer);
    }

    public static void clearOffer(HttpSession session) {
        session.setAttribute(OFFER_ATTRIBUTE, null);
    }
}
