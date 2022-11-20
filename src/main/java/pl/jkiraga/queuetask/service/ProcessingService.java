package pl.jkiraga.queuetask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import pl.jkiraga.queuetask.model.dict.RequestStatus;
import pl.jkiraga.queuetask.model.entity.ResourceDownloadRequest;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessingService {

    private HttpDownloadService httpDownloadService;
    private DatabaseService databaseService;

    @Transactional
    public void processQueuedResource(Integer requestId) {
        ResourceDownloadRequest request =  databaseService.getResourceDownloadRequestById(requestId)
                .orElseThrow(EntityNotFoundException::new);
        String content = httpDownloadService.getContentOfResource(request.getUrl());
        databaseService.saveResourceAndUpdateResourceDownloadRequest(request, content);
    }
}
