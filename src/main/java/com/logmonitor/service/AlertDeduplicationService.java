package com.logmonitor.service;

import com.logmonitor.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AlertDeduplicationService {

    private final AlertRepository alertRepository;

    public AlertDeduplicationService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public boolean isDuplicate(String dedupKey) {
        LocalDateTime cutoff = LocalDateTime.now().minusSeconds(60);
        return alertRepository.existsByDedupKeyIgnoreCaseAndTimestampAfter(dedupKey, cutoff);
    }
}