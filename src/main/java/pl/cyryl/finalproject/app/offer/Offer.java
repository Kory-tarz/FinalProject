package pl.cyryl.finalproject.app.offer;


import lombok.Getter;
import lombok.Setter;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.transaction.TransactionType;
import pl.cyryl.finalproject.users.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Setter
@Getter
@Entity
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private User submittingUser;
    @ManyToOne
    private User receivingUser;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Item> submittedItems;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<Item> offeredItems;
    @ManyToOne
    private TransactionType transactionType;
    private LocalDateTime lastUpdated;
    @ManyToOne
    @NotNull
    private Status status;

    @PrePersist
    private void onSave(){
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate(){
        lastUpdated = LocalDateTime.now();
    }

}
