package com.logmonitor.controller;

import com.logmonitor.model.Alert;
import com.logmonitor.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<Alert> getAlerts(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String type
    ) {
        if (service != null && !service.isBlank()) {
            return alertService.getAlertsByService(service);
        }

        if (type != null && !type.isBlank()) {
            return alertService.getAlertsByType(type);
        }

        return alertService.getAllAlerts();
    }
}