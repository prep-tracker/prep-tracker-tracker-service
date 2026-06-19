package com.prept.tracker.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roadmaps")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Roadmap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderIndex ASC")
    @Builder.Default
    private List<RoadmapItem> items = new ArrayList<>();
}
