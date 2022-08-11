package pl.cyryl.finalproject.app.item;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.cyryl.finalproject.app.category.CategoryRepository;

@RequestMapping("/item")
@Controller
public class ItemController {
    private final ItemService itemService;
    private final CategoryRepository categoryRepository;
    private final String ITEM_ATTRIBUTE = "item";
    private final String CATEGORIES = "categories";

    public ItemController(ItemService itemService, CategoryRepository categoryRepository) {
        this.itemService = itemService;
        this.categoryRepository = categoryRepository;
    }

    @GetMapping("/add")
    public String createNewItem(Model model){
        model.addAttribute(ITEM_ATTRIBUTE, new Item());
        model.addAttribute(CATEGORIES, categoryRepository.findAll());
        return "item/add";
    }
}
