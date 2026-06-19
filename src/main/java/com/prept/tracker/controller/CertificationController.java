package com.prept.tracker.controller;

import com.prept.tracker.context.UserContext;

import com.prept.tracker.domain.enums.CertificationStatus;
import com.prept.tracker.dto.common.PageResponse;
import com.prept.tracker.dto.request.CertificationRequest;
import com.prept.tracker.dto.response.CertificationResponse;

import com.prept.tracker.service.CertificationService;
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

import java.util.List;

@RestController
@RequestMapping("/api/v1/certifications")
@RequiredArgsConstructor
@Validated
@Tag(name = "Certifications", description = "Certification management endpoints")
public class CertificationController {

    private final CertificationService certificationService;
    

    @GetMapping
    @Operation(summary = "Get all certifications", description = "Retrieve all certifications for the current user")
    public ResponseEntity<PageResponse<CertificationResponse>> getAll(
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(certificationService.getByUser(userId, pageable));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get certification by ID", description = "Retrieve a specific certification by its ID")
    public ResponseEntity<CertificationResponse> getById(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(certificationService.getById(userId, id));
    }

    @GetMapping("/status/{status}")
    @Operation(summary = "Get certifications by status", description = "Retrieve certifications filtered by status")
    public ResponseEntity<PageResponse<CertificationResponse>> getByStatus(
            @PathVariable CertificationStatus status,
            @PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(certificationService.getByUserAndStatus(userId, status, pageable));
    }

    @GetMapping("/upcoming")
    @Operation(summary = "Get upcoming certifications", description = "Retrieve certifications scheduled within the next 3 months")
    public ResponseEntity<List<CertificationResponse>> getUpcoming() {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(certificationService.getUpcoming(userId, java.time.LocalDate.now().plusMonths(3)));
    }

    @PostMapping
    @Operation(summary = "Create a new certification", description = "Create a new certification")
    public ResponseEntity<CertificationResponse> create(@Valid @RequestBody CertificationRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.status(HttpStatus.CREATED).body(certificationService.create(userId, request));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a certification", description = "Update an existing certification")
    public ResponseEntity<CertificationResponse> update(@PathVariable Long id, @Valid @RequestBody CertificationRequest request) {
        Long userId = UserContext.getUserId();
        return ResponseEntity.ok(certificationService.update(userId, id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a certification", description = "Delete a certification by its ID")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = UserContext.getUserId();
        certificationService.delete(userId, id);
        return ResponseEntity.noContent().build();
    }
}

