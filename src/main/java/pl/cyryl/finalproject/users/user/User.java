package pl.cyryl.finalproject.users.user;

import lombok.Getter;
import lombok.Setter;
import pl.cyryl.finalproject.app.photo.Photo;
import pl.cyryl.finalproject.users.role.Role;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
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
    @NotNull
    private String password;
    @NotEmpty
    private String firstName;
    @NotEmpty
    private String lastName;
    @Email
    private String email;
    private String address;
    private String about;
    private String location;
    private String phoneNumber;
    @ManyToOne
    private Photo profilePhotoFile;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles;
}
