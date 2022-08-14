package pl.cyryl.finalproject.users.user.verification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.cyryl.finalproject.users.user.User;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    Optional<VerificationToken> findByUser(User user);
    Optional<VerificationToken> findByToken(String token);
}
