package pl.cyryl.finalproject.app.photo.ProfilePicture;

import org.springframework.stereotype.Service;
import pl.cyryl.finalproject.app.photo.PhotoRepository;
import pl.cyryl.finalproject.app.photo.PhotoService;
import pl.cyryl.finalproject.util.FilesUtil;

import java.util.List;

@Service
public class ProfilePictureService extends PhotoService<ProfilePicture> {

    private final ProfilePictureRepository pictureRepository;

    protected ProfilePictureService(FilesUtil filesUtil, ProfilePictureRepository pictureRepository) {
        super(filesUtil, ProfilePicture.class);
        this.pictureRepository = pictureRepository;
    }

    @Override
    protected PhotoRepository<ProfilePicture> getPhotoRepository() {
        return pictureRepository;
    }

    public List<ProfilePicture> findPublicProfilePictures(){
        return pictureRepository.findByPublicPictureTrue();
    }
}
