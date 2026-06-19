package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.NoteRequest;
import com.prept.tracker.dto.response.NoteResponse;

import com.prept.tracker.service.NoteService;
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
@RequestMapping("/api/v1/notes")
@RequiredArgsConstructor
@Validated
@Tag(name = "Notes", description = "Note management endpoints")
public class NoteController {

    private final NoteService noteService;
    

    @GetMapping
    @Operation(summary = "Get all notes", description = "Retrieve all notes for the current user")
    public ResponseEntity<PageResponse<NoteResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(noteService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get note by ID", description = "Retrieve a specific note by its ID")
    public ResponseEntity<NoteResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(noteService.getById(userId, id));
    }

    @GetMapping("/tag/{tag}")
    @Operation(summary = "Get notes by tag", description = "Retrieve notes filtered by tag")
    public ResponseEntity<PageResponse<NoteResponse>> getByTag(
            @PathVariable String tag,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(noteService.getByUserAndTag(userId, tag, pageable));
    }

    @GetMapping("/search")
    @Operation(summary = "Search notes", description = "Search notes by title")
    public ResponseEntity<PageResponse<NoteResponse>> search(
            @RequestParam String query,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(noteService.search(userId, query, pageable));
    }

    @PostMapping
    @Operation(summary = "Create a new note", description = "Create a new note")
    public ResponseEntity<NoteResponse> create(@Valid @RequestBody NoteRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a note", description = "Update an existing note")
    public ResponseEntity<NoteResponse> update(@PathVariable Long id, @Valid @RequestBody NoteRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(noteService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a note", description = "Delete a note by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        noteService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

