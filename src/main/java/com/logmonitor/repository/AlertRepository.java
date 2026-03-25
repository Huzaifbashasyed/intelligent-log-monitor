package com.logmonitor.repository;

import com.logmonitor.model.Alert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert, String> {

    List<Alert> findByServiceIgnoreCase(String service);

    List<Alert> findByTypeIgnoreCase(String type);

    boolean existsByDedupKeyIgnoreCaseAndTimestampAfter(String dedupKey, LocalDateTime cutoffTime);
}