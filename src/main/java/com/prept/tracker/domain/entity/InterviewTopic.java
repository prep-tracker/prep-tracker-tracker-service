package com.prept.tracker.domain.entity;

import com.prept.tracker.domain.enums.Difficulty;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "interview_topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InterviewTopic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "total_questions", nullable = false)
    @Builder.Default
    private Integer totalQuestions = 0;

    @Column(name = "solved_questions", nullable = false)
    @Builder.Default
    private Integer solvedQuestions = 0;

    @Column(name = "revision_count", nullable = false)
    @Builder.Default
    private Integer revisionCount = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
