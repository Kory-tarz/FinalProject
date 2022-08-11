package pl.cyryl.finalproject.users.user;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;
import pl.cyryl.finalproject.users.role.Role;
import pl.cyryl.finalproject.app.photo.ProfilePicture;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Setter
@Getter
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
    @Column(unique = true)
    private String email;
    private String address;
    private String about;
    private String location;
    private String phoneNumber;
    @ManyToOne
    private ProfilePicture profilePhotoFile;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;
}
