package com.prept.tracker.domain.entity;

import com.prept.tracker.domain.enums.PlanPriority;
import com.prept.tracker.domain.enums.PlanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "preparation_plans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreparationPlan extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "goal", nullable = false)
    private String goal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id")
    private Resource resource;

    @Column(name = "target_completion_date")
    private LocalDate targetCompletionDate;

    @Column(name = "estimated_hours")
    private Integer estimatedHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    @Builder.Default
    private PlanPriority priority = PlanPriority.MEDIUM;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private PlanStatus status = PlanStatus.NOT_STARTED;

    @Column(name = "progress", nullable = false)
    @Builder.Default
    private Integer progress = 0;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
