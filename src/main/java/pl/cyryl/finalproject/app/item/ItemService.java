package pl.cyryl.finalproject.app.item;

import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ItemService {
    List<Item> findItemsFromUser(long userId);
    List<Item> findVisibleItemsFromUser(long userId);
    void save(Item item);
    void saveEdited(Item item);
    Optional<Item> findItem(long id);
    Optional<Item> findPublicItem(long id);
    List<Item> findAllActive();
    Page<Item> findAllActive(int pageNumber, int itemsPerPage);
    Page<Item> findAllActive(int pageNumber, int itemsPerPage, int categoryId, String columnToSortBy, boolean asc);
}