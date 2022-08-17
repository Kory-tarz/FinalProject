package pl.cyryl.finalproject.app.offer.status;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Setter
@Getter
@Entity
public class Status {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String status;

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Status){
            return ((Status) obj).name.equals(this.name);
        }
        return false;
    }
}
