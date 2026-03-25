package com.logmonitor.repository;

import com.logmonitor.model.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntry, String> {

    List<LogEntry> findByServiceIgnoreCase(String service);

    List<LogEntry> findByLevelIgnoreCase(String level);
}