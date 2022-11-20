package pl.jkiraga.queuetask.queue;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pl.jkiraga.queuetask.service.ProcessingService;

@Component
@Slf4j
@AllArgsConstructor
public class QueueReceiver {

    private ProcessingService processingService;

    @SuppressWarnings("unused")
    public void receiveRequestId(Integer requestId)  {
        log.info("Received request {} from queue. Processing started.", requestId);
        processingService.processQueuedResource(requestId);
        log.info("Processing finished for id {}.", requestId);
    }
}