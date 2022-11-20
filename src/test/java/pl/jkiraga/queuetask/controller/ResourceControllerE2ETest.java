package pl.jkiraga.queuetask.controller;

import org.junit.jupiter.api.Test;
import org.openapitools.model.ResourceDownloadRequestDTO;
import org.openapitools.model.ResourceDownloadResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import pl.jkiraga.queuetask.repository.ResourceDownloadRequestRepository;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@AutoConfigureTestDatabase
public class ResourceControllerE2ETest {

    private static final String URL = "/resource/download";

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ResourceDownloadRequestRepository resourceDownloadRequestRepository;

    @Test
    void httpPost_saveDownloadRequest() {
        // given
        HttpEntity<ResourceDownloadRequestDTO> request = getHttpEntity("https://www.onet.pl");

        // when
        ResponseEntity<ResourceDownloadResponseDTO> result  =
                restTemplate.postForEntity(
                        createURLWithPort(URL),
                        request,
                        ResourceDownloadResponseDTO.class);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.OK.value());
        assertThat(Objects.requireNonNull(result.getBody()).getMessage()).isEqualTo("Request saved successfully");
        assertThat(resourceDownloadRequestRepository.findAll().size()).isEqualTo(1);
    }

    @Test
    void httpPost_saveDownloadRequest_invalidUrl() {
        // given
        HttpEntity<ResourceDownloadRequestDTO> request = getHttpEntity("www.onet.pl");

        // when
        ResponseEntity<ResourceDownloadResponseDTO> result  =
                restTemplate.postForEntity(
                        createURLWithPort(URL),
                        request,
                        ResourceDownloadResponseDTO.class);

        // then
        assertThat(result.getStatusCodeValue()).isEqualTo(HttpStatus.BAD_REQUEST.value());
        assertThat(Objects.requireNonNull(result.getBody()).getMessage()).isEqualTo("Invalid url. Url must start with 'https://'.");
        assertThat(resourceDownloadRequestRepository.findAll().size()).isEqualTo(0);
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

    private HttpEntity<ResourceDownloadRequestDTO> getHttpEntity(String url) {
        ResourceDownloadRequestDTO requestDTO = new ResourceDownloadRequestDTO();
        requestDTO.setUrl(url);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return new HttpEntity<>(requestDTO, headers);
    }
}
