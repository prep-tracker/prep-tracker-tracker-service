package com.prept.tracker.dto.response;

import com.prept.tracker.domain.enums.CertificationStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationResponse {

    private Long id;
    private String name;
    private String provider;
    private LocalDate examDate;
    private LocalDate targetDate;
    private BigDecimal cost;
    private Integer progress;
    private CertificationStatus status;
    private Instant createdAt;
    private Instant modifiedAt;
}