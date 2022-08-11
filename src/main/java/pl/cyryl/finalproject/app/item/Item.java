package pl.cyryl.finalproject.app.item;


import lombok.Getter;
import lombok.Setter;
import pl.cyryl.finalproject.app.category.Category;
import pl.cyryl.finalproject.app.photo.ItemPhoto;
import pl.cyryl.finalproject.users.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User owner;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    private String expectations;
    @ManyToOne(cascade = CascadeType.ALL)
    private Category category;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ItemPhoto> itemPhotos;
    @NotNull
    public boolean publicVisibility = true;
    @NotNull
    public boolean active = true;

    @Override
    public boolean equals(Object obj) {
        if(!(obj instanceof Item)){
            return false;
        }
        return this.id == ((Item) obj).getId();
    }

    @Override
    public int hashCode() {
        return Long.valueOf(id).hashCode();
    }
}
