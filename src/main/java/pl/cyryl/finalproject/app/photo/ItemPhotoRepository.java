package pl.cyryl.finalproject.app.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemPhotoRepository extends PhotoRepository<ItemPhoto>{
}
