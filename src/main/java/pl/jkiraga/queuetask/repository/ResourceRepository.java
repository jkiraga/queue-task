package pl.jkiraga.queuetask.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.jkiraga.queuetask.model.entity.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Integer> {
}
