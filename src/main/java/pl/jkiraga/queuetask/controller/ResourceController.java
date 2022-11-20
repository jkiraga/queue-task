package pl.jkiraga.queuetask.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.openapitools.api.ResourceApi;
import org.openapitools.model.ResourceDownloadRequestDTO;
import org.openapitools.model.ResourceDownloadResponseDTO;
import org.springframework.amqp.AmqpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pl.jkiraga.queuetask.model.dict.RequestStatus;
import pl.jkiraga.queuetask.queue.QueueProducer;
import pl.jkiraga.queuetask.service.DatabaseService;
import pl.jkiraga.queuetask.service.ValidationService;

@RestController
@Slf4j
@AllArgsConstructor
public class ResourceController implements ResourceApi {

    private ValidationService validationService;
    private DatabaseService databaseService;
    private QueueProducer queueProducer;

    @Override
    public ResponseEntity<ResourceDownloadResponseDTO> saveDownloadRequest(ResourceDownloadRequestDTO resourceDownloadRequestDTO) {
        validationService.validateResourceDownloadRequest(resourceDownloadRequestDTO);
        var resourceDownloadRequestEntity = databaseService.saveResourceDownloadRequest(resourceDownloadRequestDTO);

        try {
            queueProducer.addToQueue(resourceDownloadRequestEntity.getId());
        } catch (AmqpException exception) {
            log.info("Error when adding resource {} to queue", resourceDownloadRequestEntity.getId(), exception);
            databaseService.setRequestStatus(resourceDownloadRequestEntity, RequestStatus.ERROR);
        }
        return ResponseEntity.ok(new ResourceDownloadResponseDTO().message("Request saved successfully"));
    }
}
