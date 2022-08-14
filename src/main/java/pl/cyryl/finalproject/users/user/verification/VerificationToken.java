package pl.cyryl.finalproject.users.user.verification;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.cyryl.finalproject.users.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class VerificationToken {

    private final static int ONE_DAY = 1;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToOne
    private User user;
    private LocalDateTime expiryDate;

    public VerificationToken(User user, String token){
        this.user = user;
        this.token = token;
        expiryDate = calculateExpiryDate();
    }

    private LocalDateTime calculateExpiryDate(){
        return LocalDateTime.now().plusDays(ONE_DAY);
    }

    public boolean isActive(){
        return LocalDateTime.now().compareTo(expiryDate) < 0;
    }
}
