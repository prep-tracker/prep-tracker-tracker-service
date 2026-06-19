package com.prept.tracker.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudySessionRequest {

    @NotNull(message = "Session date is required")
    private LocalDate sessionDate;

    @Min(value = 1, message = "Duration must be at least 1 minute")
    private Integer durationMinutes;

    private String notes;
    private String topicCovered;
    private Long resourceId;
}