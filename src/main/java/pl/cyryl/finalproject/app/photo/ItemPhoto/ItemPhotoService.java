package pl.cyryl.finalproject.app.photo.ItemPhoto;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.app.photo.PhotoRepository;
import pl.cyryl.finalproject.app.photo.PhotoService;
import pl.cyryl.finalproject.util.FilesService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemPhotoService extends PhotoService<ItemPhoto> {

    private final ItemPhotoRepository itemPhotoRepository;

    public ItemPhotoService(ItemPhotoRepository itemPhotoRepository, FilesService filesService) {
        super(filesService, ItemPhoto.class);
        this.itemPhotoRepository = itemPhotoRepository;
    }

    public List<ItemPhoto> loadMultiplePhotos(MultipartFile[] multipartFiles) throws IOException {
        List<ItemPhoto> photos = new ArrayList<>();
        for (MultipartFile file : multipartFiles){
            if(!file.isEmpty()) {
                photos.add(this.savePhotoFromFile(new ItemPhoto(), file));
            }
        }
        return photos;
    }

    @Override
    protected PhotoRepository<ItemPhoto> getPhotoRepository() {
        return itemPhotoRepository;
    }
}
