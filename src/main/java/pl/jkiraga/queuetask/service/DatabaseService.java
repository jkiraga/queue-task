package pl.jkiraga.queuetask.service;

import lombok.AllArgsConstructor;
import org.openapitools.model.ResourceDownloadRequestDTO;
import org.springframework.stereotype.Service;
import pl.jkiraga.queuetask.model.dict.RequestStatus;
import pl.jkiraga.queuetask.model.entity.Resource;
import pl.jkiraga.queuetask.model.entity.ResourceDownloadRequest;
import pl.jkiraga.queuetask.repository.ResourceDownloadRequestRepository;
import pl.jkiraga.queuetask.repository.ResourceRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DatabaseService {

    private ResourceDownloadRequestRepository resourceDownloadRequestRepository;
    private ResourceRepository resourceRepository;

    public List<Integer> getIdsWithError() {
        return resourceDownloadRequestRepository.getIdsWithQueueErrorStatus();
    }

    public Optional<ResourceDownloadRequest> getResourceDownloadRequestById(Integer id) {
        return resourceDownloadRequestRepository.findById(id);
    }

    @Transactional
    public void saveResourceAndUpdateResourceDownloadRequest(ResourceDownloadRequest resourceDownloadRequest, String content) {
        var newResource = new Resource();
        newResource.setResourceDownloadRequest(resourceDownloadRequest);
        newResource.setContent(content);
        resourceRepository.save(newResource);
        setRequestStatus(resourceDownloadRequest, RequestStatus.FINISHED);
    }


    public ResourceDownloadRequest saveResourceDownloadRequest(ResourceDownloadRequestDTO requestDTO) {
        return resourceDownloadRequestRepository.save(createNewResourceDownloadRequest(requestDTO));
    }

    public void setRequestStatus(ResourceDownloadRequest requestToUpdate, RequestStatus processing) {
        requestToUpdate.setStatus(processing);
        resourceDownloadRequestRepository.save(requestToUpdate);
    }

    private ResourceDownloadRequest createNewResourceDownloadRequest(ResourceDownloadRequestDTO requestDTO) {
        var resourceDownloadRequest = new ResourceDownloadRequest();
        resourceDownloadRequest.setUrl(requestDTO.getUrl());
        resourceDownloadRequest.setStatus(RequestStatus.NEW);

        return resourceDownloadRequest;
    }
}
