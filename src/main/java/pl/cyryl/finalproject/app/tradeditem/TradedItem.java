package pl.cyryl.finalproject.app.tradeditem;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.photo.Photo;
import pl.cyryl.finalproject.users.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TradedItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User owner;
    @NotEmpty
    private String name;
    @NotEmpty
    private String description;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Photo> photos;

    public TradedItem(Item item){
        this.owner = item.getOwner();
        this.name = item.getName();
        this.description = item.getDescription();
        this.photos = item.getPhotos();
    }
}
