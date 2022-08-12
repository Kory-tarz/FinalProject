package pl.cyryl.finalproject.app.offer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cyryl.finalproject.app.offer.status.Status;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findAllBySubmittingUserIdAndStatus(long submittingUserId, Status status);
    List<Offer> findAllByReceivingUserIdAndStatus(long receivingUserId, Status status);
}
