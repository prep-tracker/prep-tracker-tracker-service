package com.prept.tracker.dto.request;

import com.prept.tracker.domain.enums.CertificationStatus;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CertificationRequest {

    @NotBlank(message = "Name is required")
    private String name;

    private String provider;
    private LocalDate examDate;
    private LocalDate targetDate;
    private BigDecimal cost;

    @Min(0)
    @Max(100)
    private Integer progress;

    @NotNull(message = "Status is required")
    private CertificationStatus status;
}