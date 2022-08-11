package pl.cyryl.finalproject.app.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProfilePictureRepository extends JpaRepository<ProfilePicture, Long> {
    List<ProfilePicture> findByPublicPictureTrue();
}
