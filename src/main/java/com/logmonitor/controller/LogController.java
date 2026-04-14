package com.logmonitor.controller;

import com.logmonitor.model.LogEntry;
import com.logmonitor.repository.LogRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:5178")
public class LogController {

    private final LogRepository logRepository;

    public LogController(LogRepository logRepository) {
        this.logRepository = logRepository;
    }

    // ✅ GET - Fetch logs from DB
    @GetMapping
    public List<LogEntry> getLogs() {
        return logRepository.findAll();
    }

    // ✅ POST - Save log to DB
    @PostMapping
    public LogEntry addLog(@RequestBody LogEntry log) {
        log.setId(UUID.randomUUID()); // ✅ FIXED
        return logRepository.save(log);

    }

}