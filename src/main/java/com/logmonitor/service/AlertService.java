package com.logmonitor.service;

import com.logmonitor.model.Alert;
import com.logmonitor.repository.AlertRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;
    private final AlertDeduplicationService alertDeduplicationService;

    public AlertService(AlertRepository alertRepository,
                        AlertDeduplicationService alertDeduplicationService) {
        this.alertRepository = alertRepository;
        this.alertDeduplicationService = alertDeduplicationService;
    }

    public void saveAlert(Alert alert) {
        if (alert.getDedupKey() != null && alertDeduplicationService.isDuplicate(alert.getDedupKey())) {
            return;
        }
        alertRepository.save(alert);
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
    }

    public List<Alert> getAlertsByService(String service) {
        return alertRepository.findByServiceIgnoreCase(service);
    }

    public List<Alert> getAlertsByType(String type) {
        return alertRepository.findByTypeIgnoreCase(type);
    }
}