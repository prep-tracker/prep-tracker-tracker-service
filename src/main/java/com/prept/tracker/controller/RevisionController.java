package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.RevisionRequest;
import com.prept.tracker.dto.response.RevisionResponse;

import com.prept.tracker.service.RevisionService;
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

@RestController
@RequestMapping("/api/v1/revisions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Revisions", description = "Revision management endpoints")
public class RevisionController {

    private final RevisionService revisionService;
    

    @GetMapping
    @Operation(summary = "Get all revisions", description = "Retrieve all revisions for the current user")
    public ResponseEntity<PageResponse<RevisionResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(revisionService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get revision by ID", description = "Retrieve a specific revision by its ID")
    public ResponseEntity<RevisionResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(revisionService.getById(userId, id));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming revisions", description = "Retrieve revisions with upcoming reminders")
    public ResponseEntity<PageResponse<RevisionResponse>> getUpcoming() {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(revisionService.getUpcomingReminders(userId, LocalDate.now().plusDays(7)));
    }

    @PostMapping
    @Operation(summary = "Create a new revision", description = "Create a new revision")
    public ResponseEntity<RevisionResponse> create(@Valid @RequestBody RevisionRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(revisionService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a revision", description = "Update an existing revision")
    public ResponseEntity<RevisionResponse> update(@PathVariable Long id, @Valid @RequestBody RevisionRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(revisionService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a revision", description = "Delete a revision by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        revisionService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

