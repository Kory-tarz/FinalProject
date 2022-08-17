package pl.cyryl.finalproject.users.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.cyryl.finalproject.users.authentication.userData.CustomOAuth2User;
import pl.cyryl.finalproject.users.role.Role;
import pl.cyryl.finalproject.app.photo.ProfilePicture.ProfilePicture;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String username;
    @Length(min = 8, max = 100)
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    @NotEmpty
    @Column(unique = true)
    private String email;
    private String address;
    private String about;
    private String location;
    private String phoneNumber;
    @ManyToOne
    private ProfilePicture profilePhotoFile;
    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<Role> roles;
    private LocalDate creationDate;
    private boolean enabled = false;

    public User(CustomOAuth2User oAuth2User){
        this.firstName = oAuth2User.getFirstName();
        this.lastName = oAuth2User.getLastName();
        this.email = oAuth2User.getEmail();
        this.username = oAuth2User.getUsername();
        this.password = Integer.toHexString(this.hashCode());
        this.enabled = true;
    }

    @PrePersist
    private void onSave(){
        creationDate = LocalDate.now();
    }
}
