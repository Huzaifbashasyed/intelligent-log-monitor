package com.logmonitor.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {

    @GetMapping("/")
    public Map<String, String> home() {
        return Map.of(
                "message", "Intelligent Log Monitoring & Incident Detection System is running",
                "logsApi", "/api/logs",
                "alertsApi", "/api/alerts",
                "dashboardOverview", "/api/dashboard/overview"
        );
    }
}