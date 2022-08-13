package pl.cyryl.finalproject.app.photo.ItemPhoto;


import lombok.Getter;
import lombok.Setter;
import pl.cyryl.finalproject.app.photo.Photo;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Setter
@Getter
@Entity
public class ItemPhoto extends Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String path;
    private boolean isMainPhoto = false;
}
