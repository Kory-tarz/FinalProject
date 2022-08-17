package pl.cyryl.finalproject.app.photo;

import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.util.FilesService;

import javax.transaction.Transactional;
import java.io.IOException;

public abstract class PhotoService<T extends Photo> {

    protected final FilesService filesService;
    private final Class<T> clazz;

    protected PhotoService(FilesService filesService, Class<T> clazz) {
        this.clazz = clazz;
        this.filesService = filesService;
    }

    protected abstract PhotoRepository<T> getPhotoRepository();

    @Transactional
    public T savePhotoFromFile(T photo, MultipartFile multipartFile) throws IOException {
        photo = filesService.getPhotoWithPath(photo, multipartFile);
        photo = getPhotoRepository().save(photo);
        filesService.saveItemPhoto(photo, multipartFile);
        return photo;
    }

    public String getDirectory(){
        return filesService.getObjectDirectory(clazz);
    }
}
