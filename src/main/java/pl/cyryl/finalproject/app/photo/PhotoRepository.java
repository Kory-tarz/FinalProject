package pl.cyryl.finalproject.app.photo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface PhotoRepository <T extends Photo> extends JpaRepository <T, Long> {

}
