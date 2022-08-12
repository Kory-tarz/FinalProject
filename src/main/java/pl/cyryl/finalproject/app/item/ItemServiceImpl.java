package pl.cyryl.finalproject.app.item;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ItemServiceImpl implements ItemService{
    private final ItemRepository itemRepository;

    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
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
    public Optional<Item> findItem(long id) {
        // TODO only active item?
        return itemRepository.findById(id);
    }

    @Override
    public Optional<Item> findPublicItem(long id){
        return itemRepository.findByIdAndActiveTrueAndPublicVisibilityTrue(id);
    }
}
