package com.prept.tracker.domain.entity;

import com.prept.tracker.domain.enums.Difficulty;
import com.prept.tracker.domain.enums.ResourceType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resource extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private ResourceType type;

    @Column(name = "url", columnDefinition = "TEXT")
    private String url;

    @Column(name = "author")
    private String author;

    @Column(name = "estimated_hours")
    private Integer estimatedHours;

    @Enumerated(EnumType.STRING)
    @Column(name = "difficulty")
    private Difficulty difficulty;

    @ElementCollection
    @CollectionTable(name = "resource_tags", joinColumns = @JoinColumn(name = "resource_id"))
    @Column(name = "tag")
    @Builder.Default
    private List<String> tags = new ArrayList<>();

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
}
