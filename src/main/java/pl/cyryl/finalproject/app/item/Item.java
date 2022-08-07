package pl.cyryl.finalproject.app.item;


import lombok.Getter;
import lombok.Setter;
import pl.cyryl.finalproject.app.category.Category;
import pl.cyryl.finalproject.app.photo.Photo;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Setter
@Getter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private String expectations;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Photo> photos;
    private String exchangeType;
}
