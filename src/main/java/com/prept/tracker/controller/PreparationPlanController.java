package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.domain.enums.PlanStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.PreparationPlanRequest;
import com.prept.tracker.dto.response.PreparationPlanResponse;

import com.prept.tracker.service.PreparationPlanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/preparation-plans")
@RequiredArgsConstructor
@Validated
@Tag(name = "Preparation Plans", description = "Preparation plan management endpoints")
public class PreparationPlanController {

    private final PreparationPlanService preparationPlanService;
    

    @GetMapping
    @Operation(summary = "Get all preparation plans", description = "Retrieve all preparation plans for the current user")
    public ResponseEntity<PageResponse<PreparationPlanResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(preparationPlanService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get preparation plan by ID", description = "Retrieve a specific preparation plan by its ID")
    public ResponseEntity<PreparationPlanResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(preparationPlanService.getById(userId, id));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get preparation plans by status", description = "Retrieve preparation plans filtered by status")
    public ResponseEntity<PageResponse<PreparationPlanResponse>> getByStatus(
            @PathVariable PlanStatus status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(preparationPlanService.getByUserAndStatus(userId, status, pageable));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue preparation plans", description = "Retrieve all overdue preparation plans")
    public ResponseEntity<List<PreparationPlanResponse>> getOverdue() {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(preparationPlanService.getOverduePlans(userId, LocalDate.now()));
    }

    @PostMapping
    @Operation(summary = "Create a new preparation plan", description = "Create a new preparation plan")
    public ResponseEntity<PreparationPlanResponse> create(@Valid @RequestBody PreparationPlanRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(preparationPlanService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a preparation plan", description = "Update an existing preparation plan")
    public ResponseEntity<PreparationPlanResponse> update(@PathVariable Long id, @Valid @RequestBody PreparationPlanRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(preparationPlanService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a preparation plan", description = "Delete a preparation plan by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        preparationPlanService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

