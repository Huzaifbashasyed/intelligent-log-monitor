package com.logmonitor.controller;

import com.logmonitor.model.LogEntry;
import com.logmonitor.service.LogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final LogService logService;

    public LogController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping
    public String addLog(@RequestBody LogEntry logEntry) {
        logService.saveLog(logEntry);
        return "Log received successfully";
    }

    @GetMapping
    public List<LogEntry> getLogs(@RequestParam(required = false) String service) {
        if (service != null && !service.isBlank()) {
            return logService.getLogsByService(service);
        }
        return logService.getAllLogs();
    }
}