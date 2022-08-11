package pl.cyryl.finalproject.app.photo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.app.photo.exceptions.NoPhotoException;
import pl.cyryl.finalproject.util.FilesUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ItemPhotoService extends PhotoService<ItemPhoto> {

    private final ItemPhotoRepository itemPhotoRepository;

    public ItemPhotoService(ItemPhotoRepository itemPhotoRepository, FilesUtil filesUtil) {
        super(filesUtil);
        this.itemPhotoRepository = itemPhotoRepository;
    }

    public List<ItemPhoto> loadMultiplePhotos(MultipartFile[] multipartFiles) throws IOException {
        List<ItemPhoto> photos = new ArrayList<>();
        for (MultipartFile file : multipartFiles){
            photos.add(this.savePhotoFromFile(new ItemPhoto(), file));
        }
        return photos;
    }

    @Override
    protected PhotoRepository<ItemPhoto> getPhotoRepository() {
        return itemPhotoRepository;
    }
}
