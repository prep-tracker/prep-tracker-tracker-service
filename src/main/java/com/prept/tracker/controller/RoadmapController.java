package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.RoadmapRequest;
import com.prept.tracker.dto.response.RoadmapResponse;

import com.prept.tracker.service.RoadmapService;
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

@RestController
@RequestMapping("/api/v1/roadmaps")
@RequiredArgsConstructor
@Validated
@Tag(name = "Roadmaps", description = "Roadmap management endpoints")
public class RoadmapController {

    private final RoadmapService roadmapService;
    

    @GetMapping
    @Operation(summary = "Get all roadmaps", description = "Retrieve all roadmaps for the current user")
    public ResponseEntity<PageResponse<RoadmapResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(roadmapService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get roadmap by ID", description = "Retrieve a specific roadmap by its ID")
    public ResponseEntity<RoadmapResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(roadmapService.getById(userId, id));
    }

    @PostMapping
    @Operation(summary = "Create a new roadmap", description = "Create a new roadmap")
    public ResponseEntity<RoadmapResponse> create(@Valid @RequestBody RoadmapRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(roadmapService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a roadmap", description = "Update an existing roadmap")
    public ResponseEntity<RoadmapResponse> update(@PathVariable Long id, @Valid @RequestBody RoadmapRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(roadmapService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a roadmap", description = "Delete a roadmap by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        roadmapService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

