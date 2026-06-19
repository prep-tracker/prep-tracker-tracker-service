package com.prept.tracker.dto.request;

import com.prept.tracker.domain.enums.GoalStatus;
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
public class GoalRequest {

    @NotBlank(message = "Title is required")
    private String title;

    private String description;
    private LocalDate startDate;
    private LocalDate targetDate;
    private Integer progress;

    @NotNull(message = "Status is required")
    private GoalStatus status;
}