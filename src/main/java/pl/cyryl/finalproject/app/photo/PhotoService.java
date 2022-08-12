package pl.cyryl.finalproject.app.photo;

import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.util.FilesUtil;

import javax.transaction.Transactional;
import java.io.IOException;

public abstract class PhotoService<T extends Photo> {

    protected final FilesUtil filesUtil;
    private final Class<T> clazz;

    protected PhotoService(FilesUtil filesUtil, Class<T> clazz) {
        this.clazz = clazz;
        this.filesUtil = filesUtil;
    }

    protected abstract PhotoRepository<T> getPhotoRepository();

    @Transactional
    public T savePhotoFromFile(T photo, MultipartFile multipartFile) throws IOException {
        photo = filesUtil.getPhotoWithPath(photo, multipartFile);
        photo = getPhotoRepository().save(photo);
        filesUtil.saveItemPhoto(photo, multipartFile);
        return photo;
    }

    public String getDirectory(){
        return filesUtil.getObjectDirectory(clazz);
    }
}
