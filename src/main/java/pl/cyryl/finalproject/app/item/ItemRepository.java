package pl.cyryl.finalproject.app.item;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByActiveTrueAndOwnerId(long userId);
    List<Item> findAllByActiveTrueAndPublicVisibilityTrueAndOwnerId(long ownerId);
    Optional<Item> findByIdAndActiveTrueAndPublicVisibilityTrue(long itemId);
    Optional<Item> findById(long itemId);
}
