package com.logmonitor.model;

import jakarta.persistence.*;
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
    @GeneratedValue
    private UUID id;

    private LocalDateTime timestamp;
    private String service;
    private String level;
    private String message;
    private String traceId;
    private long durationMs;
}