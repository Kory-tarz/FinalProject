package pl.cyryl.finalproject.app.offer;


import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;
import pl.cyryl.finalproject.app.item.Item;
import pl.cyryl.finalproject.app.offer.status.Status;
import pl.cyryl.finalproject.app.transaction.TransactionType;
import pl.cyryl.finalproject.users.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Item> submittedItems = new HashSet<>();
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    private Set<Item> offeredItems = new HashSet<>();
    @ManyToOne
    private TransactionType transactionType;
    private LocalDateTime lastUpdated;
    @ManyToOne
    @NotNull
    private Status status;

    @PrePersist
    private void onSave() {
        lastUpdated = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        lastUpdated = LocalDateTime.now();
    }

    public void removeItem(long id) {
        submittedItems = submittedItems.stream().filter(item -> item.getId() != id).collect(Collectors.toSet());
        offeredItems = offeredItems.stream().filter(item -> item.getId() != id).collect(Collectors.toSet());
    }

    public Offer createCounterOffer() {
        Offer counterOffer = new Offer();
        counterOffer.setId(this.getId());
        counterOffer.setReceivingUser(this.getSubmittingUser());
        counterOffer.setSubmittingUser(this.getReceivingUser());
        counterOffer.setOfferedItems(new HashSet<>(this.getSubmittedItems()));
        counterOffer.setSubmittedItems(new HashSet<>(this.getOfferedItems()));
        return counterOffer;
    }
}
