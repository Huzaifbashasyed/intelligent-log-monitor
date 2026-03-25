package com.logmonitor.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogEntry {

    @Id
    private String id;

    private LocalDateTime timestamp;
    private String service;
    private String level;
    private String message;
    private String traceId;
    private long durationMs;

    public LogEntry(LocalDateTime timestamp, String service, String level, String message, String traceId, long durationMs) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = timestamp;
        this.service = service;
        this.level = level;
        this.message = message;
        this.traceId = traceId;
        this.durationMs = durationMs;
    }
}