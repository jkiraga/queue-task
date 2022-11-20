package pl.jkiraga.queuetask.service;

import lombok.AllArgsConstructor;
import org.openapitools.model.ResourceDownloadRequestDTO;
import org.springframework.stereotype.Service;
import pl.jkiraga.queuetask.configuration.QueueTaskConfigurationProperties;
import pl.jkiraga.queuetask.controller.exceptions.TooManyRequestException;
import pl.jkiraga.queuetask.model.dict.RequestStatus;
import pl.jkiraga.queuetask.repository.ResourceDownloadRequestRepository;

@Service
@AllArgsConstructor
public class ValidationService {

    private static final String SCHEMA = "https://";
    private QueueTaskConfigurationProperties queueTaskConfigurationProperties;
    private ResourceDownloadRequestRepository resourceDownloadRequestRepository;

    public void validateResourceDownloadRequest(ResourceDownloadRequestDTO resourceDownloadRequestDTO) {
        if (isQueueTooLong()) {
            throw new TooManyRequestException("The queue of requests to save is too long. The limit is " +
                     queueTaskConfigurationProperties.getQueueMaxCapacity());
        }
        if (!isUrlValid(resourceDownloadRequestDTO)) {
            throw new IllegalArgumentException("Invalid url. Url must start with 'https://'.");
        }
    }

    private boolean isQueueTooLong() {
        return resourceDownloadRequestRepository.countAllByStatusIs(RequestStatus.NEW)
                .compareTo(queueTaskConfigurationProperties.getQueueMaxCapacity()) >= 0;
    }

    private boolean isUrlValid(ResourceDownloadRequestDTO requestDTO) {
        return !requestDTO.getUrl().isBlank() && requestDTO.getUrl().startsWith(SCHEMA);
    }
}
