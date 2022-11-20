package pl.jkiraga.queuetask.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class ProcessingCronService {

    private ProcessingService processingService;
    private DatabaseService databaseService;

    //TODO: ShedLock?
    @Scheduled(cron = "${settings.download-interval-cron}")
    public void processRequestsWithError() {
        databaseService.getIdsWithError().forEach(requestId -> {
            log.info("Cron: Found request {} with error. Processing started.", requestId);
            processingService.processQueuedResource(requestId);
            log.info("Cron: Request {} with error. Processing finished.", requestId);
        });
    }
}