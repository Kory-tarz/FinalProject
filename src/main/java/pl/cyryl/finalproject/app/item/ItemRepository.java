package pl.cyryl.finalproject.app.item;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findAllByActiveTrueAndOwnerId(long userId);

    List<Item> findAllByActiveTrueAndPublicVisibilityTrueAndOwnerId(long ownerId);

    Optional<Item> findByIdAndActiveTrueAndPublicVisibilityTrue(long itemId);

    Optional<Item> findById(long itemId);

    List<Item> findAllByActiveTrue();

    Page<Item> findAllByActiveTrue(Pageable pageable);

    Page<Item> findAllByActiveTrueAndCategoryId(long categoryId, Pageable pageable);
}
