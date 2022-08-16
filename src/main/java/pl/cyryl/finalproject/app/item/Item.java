package pl.cyryl.finalproject.app.item;


import lombok.Getter;
import lombok.Setter;
import pl.cyryl.finalproject.app.category.Category;
import pl.cyryl.finalproject.app.photo.ItemPhoto.ItemPhoto;
import pl.cyryl.finalproject.users.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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
    @ManyToOne
    private Category category;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private List<ItemPhoto> itemPhotos;
    @NotNull
    public boolean publicVisibility = true;
    @NotNull
    public boolean active = true;
    private LocalDateTime lastUpdated;

    @PrePersist
    private void onSave(){
        setUpdateTime();
    }
    @PreUpdate
    private void onUpdate(){
        setUpdateTime();
    }

    private void setUpdateTime(){
        lastUpdated = LocalDateTime.now();
    }

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

    public ItemPhoto getMainPhoto(){
        return itemPhotos.get(0);
    }

}
