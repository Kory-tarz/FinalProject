package pl.cyryl.finalproject.app.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.offer.status.Status;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllBySubmittingUserIdAndStatus(long submittingUserId, Status status);

    List<Offer> findAllByReceivingUserIdAndStatus(long receivingUserId, Status status);

    @Query("SELECT o FROM Offer o WHERE (o.submittingUser.id = :user_id OR o.receivingUser.id = :user_id) AND o.status = :status")
    List<Offer> findAllAcceptedOffers(@Param("user_id") long userId, @Param("status") Status status);

    //TODO optimize this query, get rid of already inactive offers, maybe check for all items at the same time?
    @Query("SELECT o FROM Offer o WHERE o.id <> :offer_id AND (:item MEMBER OF o.offeredItems OR :item MEMBER OF o.submittedItems)")
    List<Offer> findAllDifferentOffersWithItem(@Param("offer_id") long offerId, @Param("item") Item item);
}
