package com.prept.tracker.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "roadmap_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoadmapItem extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "order_index", nullable = false)
    @Builder.Default
    private Integer orderIndex = 0;

    @Column(name = "completed", nullable = false)
    @Builder.Default
    private Boolean completed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;
}
