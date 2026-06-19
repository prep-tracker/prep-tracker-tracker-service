package com.prept.tracker.domain.entity;

import com.prept.tracker.domain.enums.CertificationStatus;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "certifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Certification extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "provider")
    private String provider;

    @Column(name = "exam_date")
    private LocalDate examDate;

    @Column(name = "target_date")
    private LocalDate targetDate;

    @Column(name = "cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "progress", nullable = false)
    @Builder.Default
    private Integer progress = 0;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    @Builder.Default
    private CertificationStatus status = CertificationStatus.NOT_STARTED;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
