package com.logmonitor.service;

import com.logmonitor.model.LogEntry;
import com.logmonitor.repository.LogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LogService {

    private final LogRepository logRepository;
    private final DetectionService detectionService;

    public LogService(LogRepository logRepository, DetectionService detectionService) {
        this.logRepository = logRepository;
        this.detectionService = detectionService;
    }

    public void saveLog(LogEntry logEntry) {
        if (logEntry.getId() == null || logEntry.getId().isBlank()) {
            logEntry.setId(java.util.UUID.randomUUID().toString());
        }

        logRepository.save(logEntry);
        detectionService.analyze(logEntry, logRepository.findAll());
    }

    public List<LogEntry> getAllLogs() {
        return logRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    public List<LogEntry> getLogsByService(String service) {
        return logRepository.findByServiceIgnoreCase(service);
    }
}