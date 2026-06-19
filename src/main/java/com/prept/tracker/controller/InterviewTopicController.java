package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.domain.enums.Difficulty;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.InterviewTopicRequest;
import com.prept.tracker.dto.response.InterviewTopicResponse;

import com.prept.tracker.service.InterviewTopicService;
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
@RequestMapping("/api/v1/interview-topics")
@RequiredArgsConstructor
@Validated
@Tag(name = "Interview Topics", description = "Interview topic management endpoints")
public class InterviewTopicController {

    private final InterviewTopicService interviewTopicService;
    

    @GetMapping
    @Operation(summary = "Get all interview topics", description = "Retrieve all interview topics for the current user")
    public ResponseEntity<PageResponse<InterviewTopicResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(interviewTopicService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get interview topic by ID", description = "Retrieve a specific interview topic by its ID")
    public ResponseEntity<InterviewTopicResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(interviewTopicService.getById(userId, id));
    }

    @GetMapping("/difficulty/{difficulty}")
    @Operation(summary = "Get interview topics by difficulty", description = "Retrieve interview topics filtered by difficulty")
    public ResponseEntity<PageResponse<InterviewTopicResponse>> getByDifficulty(
            @PathVariable Difficulty difficulty,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(interviewTopicService.getByUserAndDifficulty(userId, difficulty, pageable));
    }

    @PostMapping
    @Operation(summary = "Create a new interview topic", description = "Create a new interview topic")
    public ResponseEntity<InterviewTopicResponse> create(@Valid @RequestBody InterviewTopicRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(interviewTopicService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update an interview topic", description = "Update an existing interview topic")
    public ResponseEntity<InterviewTopicResponse> update(@PathVariable Long id, @Valid @RequestBody InterviewTopicRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(interviewTopicService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete an interview topic", description = "Delete an interview topic by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        interviewTopicService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

