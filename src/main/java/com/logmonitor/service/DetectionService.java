package com.logmonitor.service;

import com.logmonitor.model.Alert;
import com.logmonitor.model.LogEntry;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DetectionService {

    private final AlertService alertService;

    public DetectionService(AlertService alertService) {
        this.alertService = alertService;
    }

    public void analyze(LogEntry newLog, List<LogEntry> allLogs) {
        detectHighFrequencyError(newLog, allLogs);
        detectCascadingFailure(newLog, allLogs);
    }

    private void detectHighFrequencyError(LogEntry newLog, List<LogEntry> allLogs) {
        if (!"ERROR".equalsIgnoreCase(newLog.getLevel())) {
            return;
        }

        LocalDateTime cutoff = newLog.getTimestamp().minusSeconds(60);

        long count = allLogs.stream()
                .filter(log -> "ERROR".equalsIgnoreCase(log.getLevel()))
                .filter(log -> log.getService().equalsIgnoreCase(newLog.getService()))
                .filter(log -> log.getMessage().equalsIgnoreCase(newLog.getMessage()))
                .filter(log -> log.getTimestamp().isAfter(cutoff))
                .count();

        if (count >= 5) {
            Alert alert = new Alert(
                    UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    "HIGH_FREQUENCY_ERROR",
                    newLog.getService(),
                    "Repeated error detected in " + newLog.getService()
                            + ": '" + newLog.getMessage() + "' occurred " + count + " times in last 60 seconds.",
                    "ALERT",
                    "HIGH_FREQUENCY_ERROR|" + newLog.getService() + "|" + newLog.getMessage()
            );

            alertService.saveAlert(alert);
        }
    }

    private void detectCascadingFailure(LogEntry newLog, List<LogEntry> allLogs) {
        if (!"ERROR".equalsIgnoreCase(newLog.getLevel())) {
            return;
        }

        if (!"OrderService".equalsIgnoreCase(newLog.getService())) {
            return;
        }

        LocalDateTime cutoff = newLog.getTimestamp().minusSeconds(10);

        boolean dbFailureExists = allLogs.stream()
                .filter(log -> "ERROR".equalsIgnoreCase(log.getLevel()))
                .filter(log -> "DBService".equalsIgnoreCase(log.getService()))
                .filter(log -> log.getTimestamp().isAfter(cutoff))
                .findAny()
                .isPresent();

        if (dbFailureExists) {
            Alert alert = new Alert(
                    UUID.randomUUID().toString(),
                    LocalDateTime.now(),
                    "CASCADING_FAILURE",
                    "OrderService",
                    "Potential cascading failure detected: DBService -> OrderService",
                    "ALERT",
                    "CASCADING_FAILURE|DBService|OrderService"
            );

            alertService.saveAlert(alert);
        }
    }
}