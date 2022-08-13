package pl.cyryl.finalproject.app.photo.ProfilePicture;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cyryl.finalproject.app.photo.PhotoRepository;

import java.util.List;

@Repository
public interface ProfilePictureRepository extends PhotoRepository<ProfilePicture> {
    List<ProfilePicture> findByPublicPictureTrue();
}
