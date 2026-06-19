package com.prept.tracker.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RevisionResponse {

    private Long id;
    private String subject;
    private LocalDate firstRevision;
    private LocalDate secondRevision;
    private LocalDate thirdRevision;
    private LocalDate nextReminder;
    private Instant createdAt;
    private Instant modifiedAt;
}