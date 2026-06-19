package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.dto.response.DashboardResponse;

import com.prept.tracker.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/dashboard")
@RequiredArgsConstructor
@Validated
@Tag(name = "Dashboard", description = "Dashboard endpoints")
public class DashboardController {

    private final DashboardService dashboardService;
    

    @GetMapping
    @Operation(summary = "Get dashboard data", description = "Retrieve dashboard data for the current user")
    public ResponseEntity<DashboardResponse> getDashboard() {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(dashboardService.getDashboardData(userId));
    }
}

