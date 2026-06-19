package com.prept.tracker.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "study_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudySession extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_date", nullable = false)
    private LocalDate sessionDate;

    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "topic_covered", length = 255)
    private String topicCovered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
