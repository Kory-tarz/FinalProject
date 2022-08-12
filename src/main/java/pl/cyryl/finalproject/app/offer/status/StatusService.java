package pl.cyryl.finalproject.app.offer.status;

import org.springframework.stereotype.Service;

@Service
public class StatusService {
    private final StatusRepository statusRepository;

    public StatusService(StatusRepository statusRepository) {
        this.statusRepository = statusRepository;
    }

    public Status getSubmittedStatus(){
        return statusRepository.findByName("submitted");
    }

    public Status getStatus(String statusName){
        return statusRepository.findByName(statusName);
    }
}
