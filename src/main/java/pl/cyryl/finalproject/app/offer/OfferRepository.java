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

    @Query("SELECT o FROM Offer o WHERE (o.submittingUser.id = :user_id OR o.receivingUser.id = :user_id) AND o.status IN :statuses")
    List<Offer> findAllByUserWithStatuses(@Param("user_id") long userId, @Param("statuses") List<Status> statuses);

    @Query("SELECT o FROM Offer o WHERE (o.submittingUser.id = :user_id OR o.receivingUser.id = :user_id) AND o.status = :status")
    List<Offer> findAllOffersWithStatus(@Param("user_id") long userId, @Param("status") Status status);

    @Query("SELECT o FROM Offer o WHERE o.id <> :offer_id AND o.status = :status AND (:item MEMBER OF o.offeredItems OR :item MEMBER OF o.submittedItems)")
    List<Offer> findAllDifferentOffersWithItemAndStatus(
            @Param("offer_id") long offerId, @Param("item") Item item, @Param("status") Status status);

    @Query(value = "SELECT *\n" +
            "FROM offer o\n" +
            "WHERE o.status_id = :status_id\n" +
            "  AND o.id <> :offer_id\n" +
            "  AND (o.id in\n" +
            "       (SELECT o2.offer_id\n" +
            "        FROM offer_offered_items o2\n" +
            "        WHERE o2.offered_items_id in :all_ids)\n" +
            "    OR o.id in\n" +
            "       (SELECT o3.offer_id\n" +
            "        FROM offer_submitted_items o3\n" +
            "        WHERE o3.submitted_items_id in :all_ids)\n" +
            "    )",
            nativeQuery = true)
    List<Offer> findAllOffersWithItems(@Param("all_ids") List<Long> allIds,
                                       @Param("offer_id") long offerId,
                                       @Param("status_id") long statusId);

}
