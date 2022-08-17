package pl.cyryl.finalproject.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.cyryl.finalproject.app.photo.ItemPhoto.ItemPhoto;
import pl.cyryl.finalproject.app.photo.Photo;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePicture;

import java.io.*;
import java.nio.file.*;

@Service
public class FilesService {

    @Value("${app.user.item-images.location}")
    private String itemPhotosLocation;
    @Value("${app.user.profile-pictures.location}")
    private String profilePicturesLocation;


    public <T extends Photo> String getObjectDirectory(Class<T> clazz){
        if (ItemPhoto.class.getSimpleName().equals(clazz.getSimpleName())){
            return getItemPhotosDirectory();
        }
        if(ProfilePicture.class.getSimpleName().equals(clazz.getSimpleName())){
            return getProfilePicturesDirectory();
        }
        return "";
    }

    public String getProfilePicturesDirectory(){
        return "/" + profilePicturesLocation + "/";
    }

    public String getItemPhotosDirectory(){
        return "/" + itemPhotosLocation + "/";
    }

    public <T extends Photo> T getPhotoWithPath(T photo, MultipartFile multipartFile){
        String fileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());
        photo.setPath(fileName);
        return photo;
    }

    public void saveProfilePicture(Photo photo, MultipartFile multipartFile) throws IOException {
        saveFile(profilePicturesLocation + "/" + photo.getId(), photo.getPath(), multipartFile);
    }

    public void saveItemPhoto(Photo photo, MultipartFile multipartFile) throws IOException {
        saveFile(itemPhotosLocation + "/" + photo.getId(), photo.getPath(), multipartFile);
    }

    public void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }
}
