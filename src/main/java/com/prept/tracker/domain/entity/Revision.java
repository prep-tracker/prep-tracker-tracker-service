package com.prept.tracker.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "revisions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Revision extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "subject", nullable = false)
    private String subject;

    @Column(name = "first_revision")
    private LocalDate firstRevision;

    @Column(name = "second_revision")
    private LocalDate secondRevision;

    @Column(name = "third_revision")
    private LocalDate thirdRevision;

    @Column(name = "next_reminder")
    private LocalDate nextReminder;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
