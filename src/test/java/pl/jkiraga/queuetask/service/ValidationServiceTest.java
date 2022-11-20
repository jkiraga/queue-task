package pl.jkiraga.queuetask.service;

import org.junit.jupiter.api.Test;
import org.openapitools.model.ResourceDownloadRequestDTO;
import pl.jkiraga.queuetask.configuration.QueueTaskConfigurationProperties;
import pl.jkiraga.queuetask.controller.exceptions.TooManyRequestException;
import pl.jkiraga.queuetask.model.dict.RequestStatus;
import pl.jkiraga.queuetask.repository.ResourceDownloadRequestRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

class ValidationServiceTest {

    @Test
    void validateResourceDownloadRequest_invalidUrl() {
        // given
        var requestDTO = getRequestDTO("incorrect_url");

        var mockResourceDownloadRequestRepository = mock(ResourceDownloadRequestRepository.class);
        given(mockResourceDownloadRequestRepository.countAllByStatusIs(RequestStatus.NEW)).willReturn(9);

        var mockQueueTaskConfigurationProperties = mock(QueueTaskConfigurationProperties.class);
        given(mockQueueTaskConfigurationProperties.getQueueMaxCapacity()).willReturn(10);

        var toTest = new ValidationService(mockQueueTaskConfigurationProperties, mockResourceDownloadRequestRepository);

        // when
        var exception = catchThrowable(() -> toTest.validateResourceDownloadRequest(requestDTO));

        // then
        assertThat(exception)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Invalid url. Url must start with 'https://'.");
    }

    @Test
    void validateResourceDownloadRequest_queueIsTooLong() {
        // given
        var requestDTO = getRequestDTO("https://www.onet.pl");

        var mockResourceDownloadRequestRepository = mock(ResourceDownloadRequestRepository.class);
        given(mockResourceDownloadRequestRepository.countAllByStatusIs(RequestStatus.NEW)).willReturn(9);

        var mockQueueTaskConfigurationProperties = mock(QueueTaskConfigurationProperties.class);
        given(mockQueueTaskConfigurationProperties.getQueueMaxCapacity()).willReturn(9);

        var toTest = new ValidationService(mockQueueTaskConfigurationProperties, mockResourceDownloadRequestRepository);

        // when
        var exception = catchThrowable(() -> toTest.validateResourceDownloadRequest(requestDTO));

        // then
        assertThat(exception)
                .isInstanceOf(TooManyRequestException.class)
                .hasMessageContaining("queue of requests to save is too long");
    }

    private ResourceDownloadRequestDTO getRequestDTO(String url) {
        ResourceDownloadRequestDTO requestDTO = new ResourceDownloadRequestDTO();
        requestDTO.setUrl(url);

        return requestDTO;
    }
}