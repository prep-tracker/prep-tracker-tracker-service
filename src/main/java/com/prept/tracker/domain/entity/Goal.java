package com.prept.tracker.domain.entity;

import com.prept.tracker.domain.enums.GoalStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "goals")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Goal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Column(name = "progress", nullable = false)
    @Builder.Default
    private Integer progress = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private GoalStatus status = GoalStatus.ACTIVE;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
