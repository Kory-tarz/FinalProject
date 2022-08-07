package pl.cyryl.finalproject.app.transaction;

import lombok.Getter;
import lombok.Setter;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.tradeditem.TradedItem;
import pl.cyryl.finalproject.users.user.User;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;


@Setter
@Getter
@Entity
public class PastTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User submittingUser;
    @ManyToOne
    private User receivingUser;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TradedItem> submittedItems;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<TradedItem> offeredItems;
    private LocalDate dateOfCompletion;

    @PrePersist
    public void onSave(){
        dateOfCompletion = LocalDate.now();
    }
}
