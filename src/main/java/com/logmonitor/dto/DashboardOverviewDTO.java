package com.logmonitor.dto;

import com.logmonitor.model.Alert;
import com.logmonitor.model.LogEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardOverviewDTO {

    private long totalLogs;
    private long totalAlerts;
    private long totalErrors;

    private List<ServiceHealthDTO> serviceHealth;
    private List<AlertSummaryDTO> alertSummary;

    private List<Alert> recentAlerts;
    private List<LogEntry> recentLogs;
}