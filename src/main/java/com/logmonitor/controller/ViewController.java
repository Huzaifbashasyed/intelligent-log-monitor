package com.logmonitor.controller;

import com.logmonitor.dto.DashboardOverviewDTO;
import com.logmonitor.service.DashboardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    private final DashboardService dashboardService;

    public ViewController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        DashboardOverviewDTO overview = dashboardService.getOverview();
        model.addAttribute("overview", overview);
        return "dashboard";
    }
}