package pl.cyryl.finalproject.app.offer.status;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StatusData {
    private boolean receivedByMe = false;
    private boolean receivedByOther = false;
    private boolean myOffer = true;
}
