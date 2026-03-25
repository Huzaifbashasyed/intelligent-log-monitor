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
@Table(name = "alerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alert {

    @Id
    private String id;

    private LocalDateTime timestamp;
    private String type;
    private String service;
    private String message;
    private String severity;
    private String dedupKey;

    public Alert(LocalDateTime timestamp, String type, String service, String message, String severity, String dedupKey) {
        this.id = UUID.randomUUID().toString();
        this.timestamp = timestamp;
        this.type = type;
        this.service = service;
        this.message = message;
        this.severity = severity;
        this.dedupKey = dedupKey;
    }
}