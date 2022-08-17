package pl.cyryl.finalproject.app.offer.status;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByName(String statusName);

    List<Status> findAllByStatus(String status);
}
