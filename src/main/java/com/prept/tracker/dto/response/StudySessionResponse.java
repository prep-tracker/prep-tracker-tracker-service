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
public class StudySessionResponse {

    private Long id;
    private LocalDate sessionDate;
    private Integer durationMinutes;
    private String notes;
    private String topicCovered;
    private ResourceResponse resource;
    private Instant createdAt;
    private Instant modifiedAt;
}