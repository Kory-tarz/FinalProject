package pl.cyryl.finalproject.app.item;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.app.category.CategoryRepository;
import pl.cyryl.finalproject.app.photo.ItemPhoto.ItemPhoto;
import pl.cyryl.finalproject.app.photo.ItemPhoto.ItemPhotoService;
import pl.cyryl.finalproject.users.user.UserService;
import pl.cyryl.finalproject.util.SessionUtils;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@RequestMapping("/item")
@Controller
public class ItemController {
    private final ItemService itemService;
    private final CategoryRepository categoryRepository;
    private final UserService userService;
    private final ItemPhotoService itemPhotoService;

    private final String ITEM_ATTRIBUTE = "item";
    private final String ITEM_LIST = "items";
    private final String CATEGORIES = "categories";


    public ItemController(ItemService itemService, CategoryRepository categoryRepository, UserService userService, ItemPhotoService itemPhotoService) {
        this.itemService = itemService;
        this.categoryRepository = categoryRepository;
        this.userService = userService;
        this.itemPhotoService = itemPhotoService;
    }

    @GetMapping("/add")
    public String createItemForm(Model model, HttpSession session) {
        long userId = (long) session.getAttribute("userId");
        Item item = new Item();
        item.setOwner(userService.findById(userId).orElseThrow());
        model.addAttribute(ITEM_ATTRIBUTE, item);
        model.addAttribute(CATEGORIES, categoryRepository.findAll());
        return "item/add";
    }

    @PostMapping("/add")
    public String createNewItem(Model model, @Valid Item item, BindingResult result, @RequestParam("images") MultipartFile[] multipartFiles) {
        if (result.hasErrors()) {
            actionOnError(model, item);
            return "item/add";
        }
        try {
            List<ItemPhoto> photos = itemPhotoService.loadMultiplePhotos(multipartFiles);
            item.setItemPhotos(photos);
            itemService.save(item);
        } catch (IOException e) {
            model.addAttribute("error_msg", e.getMessage());
            actionOnError(model, item);
            return "item/add";
        }
        return "redirect:/item/list";
    }

    @GetMapping("/edit/{id}")
    public String editItem(HttpSession session, Model model, @PathVariable long id){
        Item item = itemService.findItem(id).orElseThrow();
        long userId = SessionUtils.getCurrentUserId(session);
        if(userId != item.getOwner().getId()){
            //TODO throw exception e.g IllegalAccess
        }
        model.addAttribute(ITEM_ATTRIBUTE, item);
        model.addAttribute(CATEGORIES, categoryRepository.findAll());
        model.addAttribute("dirName", itemPhotoService.getDirectory());
        return "item/edit";
    }

    @PostMapping("/edit")
    public String saveEditedItem(Model model,
            @Valid Item item,
            BindingResult result,
            @RequestParam(value = "images", required = false) MultipartFile[] multipartFiles){

        if(result.hasErrors()){
            actionOnError(model, item);
            return "item/edit";
        }
        try {
            List<ItemPhoto> photos = itemPhotoService.loadMultiplePhotos(multipartFiles);
            item.getItemPhotos().addAll(photos);
            itemService.save(item);
        } catch (IOException e) {
            model.addAttribute("error_msg", e.getMessage());
            actionOnError(model, item);
            return "item/edit";
        }
        return "redirect:/item/list";
    }

    @RequestMapping("/list")
    public String listItems(HttpSession session, Model model) {
        long userId = (long) session.getAttribute("userId");
        List<Item> activeItems = itemService.findItemsFromUser(userId);
        model.addAttribute(ITEM_LIST, activeItems);
        return "item/list";
    }

    @RequestMapping("list/{id}")
    public String listItemsFromUser(Model model, @PathVariable long id) {
        List<Item> items = itemService.findVisibleItemsFromUser(id);
        model.addAttribute(ITEM_LIST, items);
        return "item/list";
    }

    @GetMapping("details/{id}")
    public String viewItemDetails(Model model, @PathVariable long id) {
        Item item = itemService.findItem(id).orElseThrow();
        model.addAttribute(ITEM_ATTRIBUTE, item);
        model.addAttribute("dirName", itemPhotoService.getDirectory());
        return "item/details";
    }

    @GetMapping("/search")
    public String search(Model model,
                         @RequestParam(value = "items_per_page", defaultValue = "10") int itemsPerPage,
                         @RequestParam(value = "page_nr", defaultValue = "0") int pageNr) {
        Page<Item> items = itemService.findAllActive(pageNr, itemsPerPage);
        model.addAttribute(ITEM_LIST, items);
        model.addAttribute("dirName", itemPhotoService.getDirectory());
        return "item/search";
    }

    private void actionOnError(Model model, Item item) {
        model.addAttribute(ITEM_ATTRIBUTE, item);
        model.addAttribute(CATEGORIES, categoryRepository.findAll());
        model.addAttribute("dirName", itemPhotoService.getDirectory());
    }
}
