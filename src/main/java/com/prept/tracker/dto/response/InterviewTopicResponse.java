package com.prept.tracker.dto.response;

import com.prept.tracker.domain.enums.Difficulty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InterviewTopicResponse {

    private Long id;
    private String topic;
    private Integer totalQuestions;
    private Integer solvedQuestions;
    private Integer revisionCount;
    private Difficulty difficulty;
    private Instant createdAt;
    private Instant modifiedAt;
}