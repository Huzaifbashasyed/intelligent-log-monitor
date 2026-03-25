package com.logmonitor.service;

import com.logmonitor.dto.AlertSummaryDTO;
import com.logmonitor.dto.DashboardOverviewDTO;
import com.logmonitor.dto.ServiceHealthDTO;
import com.logmonitor.model.Alert;
import com.logmonitor.model.LogEntry;
import com.logmonitor.repository.AlertRepository;
import com.logmonitor.repository.LogRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final LogRepository logRepository;
    private final AlertRepository alertRepository;

    public DashboardService(LogRepository logRepository, AlertRepository alertRepository) {
        this.logRepository = logRepository;
        this.alertRepository = alertRepository;
    }

    public DashboardOverviewDTO getOverview() {
        List<LogEntry> allLogs = logRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));
        List<Alert> allAlerts = alertRepository.findAll(Sort.by(Sort.Direction.DESC, "timestamp"));

        long totalLogs = allLogs.size();
        long totalAlerts = allAlerts.size();
        long totalErrors = allLogs.stream()
                .filter(log -> "ERROR".equalsIgnoreCase(log.getLevel()))
                .count();

        List<ServiceHealthDTO> serviceHealth = buildServiceHealth(allLogs);
        List<AlertSummaryDTO> alertSummary = buildAlertSummary(allAlerts);

        List<Alert> recentAlerts = allAlerts.stream().limit(5).toList();
        List<LogEntry> recentLogs = allLogs.stream().limit(10).toList();

        return new DashboardOverviewDTO(
                totalLogs,
                totalAlerts,
                totalErrors,
                serviceHealth,
                alertSummary,
                recentAlerts,
                recentLogs
        );
    }

    public List<ServiceHealthDTO> getServiceHealth() {
        return buildServiceHealth(logRepository.findAll());
    }

    public List<AlertSummaryDTO> getAlertSummary() {
        return buildAlertSummary(alertRepository.findAll());
    }

    private List<ServiceHealthDTO> buildServiceHealth(List<LogEntry> logs) {
        Map<String, List<LogEntry>> grouped = logs.stream()
                .collect(Collectors.groupingBy(LogEntry::getService));

        List<ServiceHealthDTO> result = new ArrayList<>();

        for (Map.Entry<String, List<LogEntry>> entry : grouped.entrySet()) {
            String service = entry.getKey();
            List<LogEntry> serviceLogs = entry.getValue();

            long totalLogs = serviceLogs.size();
            long errorCount = serviceLogs.stream()
                    .filter(log -> "ERROR".equalsIgnoreCase(log.getLevel()))
                    .count();

            String status;
            if (errorCount >= 5) {
                status = "CRITICAL";
            } else if (errorCount >= 2) {
                status = "WARNING";
            } else {
                status = "HEALTHY";
            }

            result.add(new ServiceHealthDTO(service, totalLogs, errorCount, status));
        }

        return result;
    }

    private List<AlertSummaryDTO> buildAlertSummary(List<Alert> alerts) {
        Map<String, Long> grouped = alerts.stream()
                .collect(Collectors.groupingBy(Alert::getType, Collectors.counting()));

        List<AlertSummaryDTO> result = new ArrayList<>();

        for (Map.Entry<String, Long> entry : grouped.entrySet()) {
            result.add(new AlertSummaryDTO(entry.getKey(), entry.getValue()));
        }

        return result;
    }
}