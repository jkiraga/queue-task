package pl.jkiraga.queuetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.jkiraga.queuetask.model.dict.RequestStatus;
import pl.jkiraga.queuetask.model.entity.ResourceDownloadRequest;

import java.util.List;

@Repository
public interface ResourceDownloadRequestRepository extends JpaRepository<ResourceDownloadRequest, Integer> {

    @Query("SELECT id " +
            "FROM ResourceDownloadRequest " +
            "WHERE status = pl.jkiraga.queuetask.model.dict.RequestStatus.ERROR")
    List<Integer> getIdsWithQueueErrorStatus();

    Integer countAllByStatusIs(RequestStatus requestStatuses);
}
