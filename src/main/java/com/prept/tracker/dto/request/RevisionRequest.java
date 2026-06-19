package com.prept.tracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionRequest {

    @NotBlank(message = "Subject is required")
    private String subject;

    private LocalDate firstRevision;
    private LocalDate secondRevision;
    private LocalDate thirdRevision;
    private LocalDate nextReminder;
}