package com.prept.tracker.dto.request;

import com.prept.tracker.domain.enums.Difficulty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTopicRequest {

    @NotBlank(message = "Topic is required")
    private String topic;

    @Min(value = 0, message = "Total questions cannot be negative")
    private Integer totalQuestions;

    @Min(value = 0, message = "Solved questions cannot be negative")
    private Integer solvedQuestions;

    private Integer revisionCount;
    private Difficulty difficulty;
}