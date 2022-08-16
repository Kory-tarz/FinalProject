package pl.cyryl.finalproject.app.item;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.util.EntityActivationService;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final EntityActivationService activationService;

    public ItemServiceImpl(ItemRepository itemRepository, EntityActivationService activationService) {
        this.itemRepository = itemRepository;
        this.activationService = activationService;
    }

    @Override
    public List<Item> findItemsFromUser(long userId) {
        return itemRepository.findAllByActiveTrueAndOwnerId(userId);
    }

    @Override
    public List<Item> findVisibleItemsFromUser(long userId) {
        return itemRepository.findAllByActiveTrueAndPublicVisibilityTrueAndOwnerId(userId);
    }

    @Override
    public void save(Item item) {
        itemRepository.save(item);
    }

    @Override
    public void saveEdited(Item item) {
        activationService.deactivateOffersWithItem(item);
        save(item);
    }

    @Override
    public Optional<Item> findItem(long id) {
        // TODO only active item?
        return itemRepository.findById(id);
    }

    @Override
    public Optional<Item> findPublicItem(long id) {
        return itemRepository.findByIdAndActiveTrueAndPublicVisibilityTrue(id);
    }

    @Override
    public List<Item> findAllActive() {
        return itemRepository.findAllByActiveTrue();
    }

    @Override
    public Page<Item> findAllActive(int pageNumber, int itemsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage);
        return itemRepository.findAllByActiveTrue(pageable);
    }

    public Page<Item> findAllActive(int pageNumber, int itemsPerPage, int categoryId, String columnToSortBy, boolean asc) {
        Pageable pageable = PageRequest.of(pageNumber, itemsPerPage, asc ? Sort.by(columnToSortBy) : Sort.by(columnToSortBy).descending());
        if (categoryId != 0) {
            return itemRepository.findAllByActiveTrueAndPublicVisibilityTrueAndCategoryId(categoryId, pageable);
        } else {
            return itemRepository.findAllByActiveTrueAndPublicVisibilityTrue(pageable);
        }
    }

    @Override
    public boolean hasItemChanged(Item item) {
        Item itemInDatabase = itemRepository.findById(item.getId()).orElseThrow();
        return !(item.getLastUpdated().equals(itemInDatabase.getLastUpdated()));
    }
}
