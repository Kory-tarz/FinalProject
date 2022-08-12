package pl.cyryl.finalproject.util;

import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.app.offer.Offer;

@Service
public class EntityActivationService {

    public void deactivateOfferItems(Offer offer){
        System.out.println("===========DEACTIVATING=========");
    }
}
