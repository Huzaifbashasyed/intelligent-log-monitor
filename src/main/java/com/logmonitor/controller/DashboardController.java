package com.logmonitor.controller;

import com.logmonitor.dto.AlertSummaryDTO;
import com.logmonitor.dto.DashboardOverviewDTO;
import com.logmonitor.dto.ServiceHealthDTO;
import com.logmonitor.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/overview")
    public DashboardOverviewDTO getOverview() {
        return dashboardService.getOverview();
    }

    @GetMapping("/service-health")
    public List<ServiceHealthDTO> getServiceHealth() {
        return dashboardService.getServiceHealth();
    }

    @GetMapping("/alert-summary")
    public List<AlertSummaryDTO> getAlertSummary() {
        return dashboardService.getAlertSummary();
    }
}