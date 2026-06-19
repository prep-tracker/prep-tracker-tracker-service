package com.prept.tracker.dto.response;

import com.prept.tracker.domain.enums.GoalStatus;
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
public class GoalResponse {

    private Long id;
    private String title;
    private String description;
    private LocalDate startDate;
    private LocalDate targetDate;
    private Integer progress;
    private GoalStatus status;
    private Instant createdAt;
    private Instant modifiedAt;
}