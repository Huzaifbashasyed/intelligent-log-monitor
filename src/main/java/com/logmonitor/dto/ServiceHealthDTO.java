package com.logmonitor.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceHealthDTO {

    private String service;
    private long totalLogs;
    private long errorCount;
    private String status; // HEALTHY / WARNING / CRITICAL
}