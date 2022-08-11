package pl.cyryl.finalproject.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.*;

public class FilesUtil {

    @Value("${app.user.item-images.location}")
    private String itemPhotosLocation;
    @Value("${app.user.profile-pictures.location}")
    private String profilePicturesLocation;


    public String getProfilePicturesDirectory(){
        return profilePicturesLocation + "/";
    }

    public String getItemPhotosDirectory(){
        return itemPhotosLocation + "/";
    }

    public void saveFile(String uploadDir, String fileName,
                                MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        System.out.println(uploadPath);

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
