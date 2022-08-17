package pl.cyryl.finalproject.app.offer.status;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public final String COMPLETED = "completed";
    public final String ACCEPTED = "accepted";
    public final String CONFIRMED_RECEIVING = "confirmed by receiving user";
    public final String CONFIRMED_SUBMITTING = "confirmed by submitting user";
    public final String SUBMITTED = "submitted";
    public final String INACTIVE = "inactive";
    public final String HISTORY = "history";
    public final String CANCELED = "canceled";

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status getSubmittedStatus() {
        return statusRepository.findByName(SUBMITTED);
    }

    public Status getStatus(String statusName) {
        return statusRepository.findByName(statusName);
    }

    public Status getAcceptedStatus() {
        return statusRepository.findByName(ACCEPTED);
    }

    public Status getInactiveStatus() {
        return statusRepository.findByName(INACTIVE);
    }

    public Status getCanceledStatus() {
        return statusRepository.findByName(CANCELED);
    }

    public Status getHistoryStatus() {
        return statusRepository.findByName(HISTORY);
    }

    public Status getConfirmedByReceivingUserStatus(){
        return statusRepository.findByName(CONFIRMED_RECEIVING);
    }

    public Status getConfirmedBySubmittingUserStatus(){
        return statusRepository.findByName(CONFIRMED_SUBMITTING);
    }

    public Status getCompletedStatus(){
        return statusRepository.findByName(COMPLETED);
    }

    public List<Status> getAllAcceptedStatuses() {
        return statusRepository.findAllByStatus(ACCEPTED);
    }

}
