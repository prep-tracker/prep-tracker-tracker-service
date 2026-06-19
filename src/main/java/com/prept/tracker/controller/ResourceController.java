package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.domain.enums.ResourceType;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.ResourceRequest;
import com.prept.tracker.dto.response.ResourceResponse;

import com.prept.tracker.service.ResourceService;
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
@RequestMapping("/api/v1/resources")
@RequiredArgsConstructor
@Validated
@Tag(name = "Resources", description = "Resource management endpoints")
public class ResourceController {

    private final ResourceService resourceService;
    

    @GetMapping
    @Operation(summary = "Get all resources", description = "Retrieve all resources for the current user")
    public ResponseEntity<PageResponse<ResourceResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(resourceService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get resource by ID", description = "Retrieve a specific resource by its ID")
    public ResponseEntity<ResourceResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(resourceService.getById(userId, id));
    }

    @GetMapping("/type/{type}")
    @Operation(summary = "Get resources by type", description = "Retrieve resources filtered by type")
    public ResponseEntity<PageResponse<ResourceResponse>> getByType(
            @PathVariable ResourceType type,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(resourceService.getByUserAndType(userId, type, pageable));
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Get resources by category", description = "Retrieve resources filtered by category")
    public ResponseEntity<PageResponse<ResourceResponse>> getByCategory(
            @PathVariable Long categoryId,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(resourceService.getByUserAndCategory(userId, categoryId, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search resources", description = "Search resources by name")
    public ResponseEntity<PageResponse<ResourceResponse>> search(
            @RequestParam String query,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(resourceService.search(userId, query, pageable));
    }

    @PostMapping
    @Operation(summary = "Create a new resource", description = "Create a new resource")
    public ResponseEntity<ResourceResponse> create(@Valid @RequestBody ResourceRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a resource", description = "Update an existing resource")
    public ResponseEntity<ResourceResponse> update(@PathVariable Long id, @Valid @RequestBody ResourceRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(resourceService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a resource", description = "Delete a resource by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        resourceService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

