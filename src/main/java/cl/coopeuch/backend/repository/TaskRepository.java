package cl.coopeuch.backend.repository;

import cl.coopeuch.backend.annotation.TimeDetector;
import cl.coopeuch.backend.entity.Task;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @TimeDetector
    @Modifying
    @Query(value = "update tasks set description = :description, enabled = :enabled where id = :id", nativeQuery = true)
    @Transactional
    int updateById (@Param("description") String description, @Param("enabled") boolean enabled, @Param("id") Long id);
}
