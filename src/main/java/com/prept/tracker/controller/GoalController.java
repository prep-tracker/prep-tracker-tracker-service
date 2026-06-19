package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.domain.enums.GoalStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.GoalRequest;
import com.prept.tracker.dto.response.GoalResponse;

import com.prept.tracker.service.GoalService;
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
@RequestMapping("/api/v1/goals")
@RequiredArgsConstructor
@Validated
@Tag(name = "Goals", description = "Goal management endpoints")
public class GoalController {

    private final GoalService goalService;
    

    @GetMapping
    @Operation(summary = "Get all goals", description = "Retrieve all goals for the current user")
    public ResponseEntity<PageResponse<GoalResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(goalService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get goal by ID", description = "Retrieve a specific goal by its ID")
    public ResponseEntity<GoalResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(goalService.getById(userId, id));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get goals by status", description = "Retrieve goals filtered by status")
    public ResponseEntity<PageResponse<GoalResponse>> getByStatus(
            @PathVariable GoalStatus status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(goalService.getByUserAndStatus(userId, status, pageable));
    }

    @GetMapping("/overdue")
    @Operation(summary = "Get overdue goals", description = "Retrieve all overdue goals")
    public ResponseEntity<List<GoalResponse>> getOverdue() {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(goalService.getOverdue(userId, LocalDate.now()));
    }

    @PostMapping
    @Operation(summary = "Create a new goal", description = "Create a new goal")
    public ResponseEntity<GoalResponse> create(@Valid @RequestBody GoalRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(goalService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a goal", description = "Update an existing goal")
    public ResponseEntity<GoalResponse> update(@PathVariable Long id, @Valid @RequestBody GoalRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(goalService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a goal", description = "Delete a goal by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        goalService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

