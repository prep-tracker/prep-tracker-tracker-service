package com.prept.tracker.dto.response;

import com.prept.tracker.domain.enums.PlanPriority;
import com.prept.tracker.domain.enums.PlanStatus;
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
public class PreparationPlanResponse {

    private Long id;
    private String goal;
    private ResourceResponse resource;
    private LocalDate targetCompletionDate;
    private Integer estimatedHours;
    private PlanPriority priority;
    private PlanStatus status;
    private Integer progress;
    private Instant createdAt;
    private Instant modifiedAt;
}