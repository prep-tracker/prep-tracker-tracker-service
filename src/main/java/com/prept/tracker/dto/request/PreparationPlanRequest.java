package com.prept.tracker.dto.request;

import com.prept.tracker.domain.enums.PlanPriority;
import com.prept.tracker.domain.enums.PlanStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
public class PreparationPlanRequest {

    @NotBlank(message = "Goal is required")
    private String goal;

    private Long resourceId;
    private LocalDate targetCompletionDate;
    private Integer estimatedHours;

    @NotNull(message = "Priority is required")
    private PlanPriority priority;

    @NotNull(message = "Status is required")
    private PlanStatus status;

    @Min(0)
    @Max(100)
    private Integer progress;
}