package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.StudySessionRequest;
import com.prept.tracker.dto.response.StudySessionResponse;

import com.prept.tracker.service.StudySessionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/v1/study-sessions")
@RequiredArgsConstructor
@Validated
@Tag(name = "Study Sessions", description = "Study session management endpoints")
public class StudySessionController {

    private final StudySessionService studySessionService;
    

    @GetMapping
    @Operation(summary = "Get all study sessions", description = "Retrieve all study sessions for the current user")
    public ResponseEntity<PageResponse<StudySessionResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(studySessionService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get study session by ID", description = "Retrieve a specific study session by its ID")
    public ResponseEntity<StudySessionResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(studySessionService.getById(userId, id));
    }

    @GetMapping("/range")
    @Operation(summary = "Get study sessions by date range", description = "Retrieve study sessions within a date range")
    public ResponseEntity<PageResponse<StudySessionResponse>> getByRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @PageableDefault(size = 20, sort = "sessionDate", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(studySessionService.getByUserAndDateRange(userId, start, end, pageable));
    }

    @PostMapping
    @Operation(summary = "Create a new study session", description = "Create a new study session")
    public ResponseEntity<StudySessionResponse> create(@Valid @RequestBody StudySessionRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(studySessionService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a study session", description = "Update an existing study session")
    public ResponseEntity<StudySessionResponse> update(@PathVariable Long id, @Valid @RequestBody StudySessionRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(studySessionService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a study session", description = "Delete a study session by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        studySessionService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

