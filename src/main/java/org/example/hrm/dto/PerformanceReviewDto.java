package org.example.hrm.dto;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.Data;
import org.example.hrm.model.Employee;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class PerformanceReviewDto {
    private Long id;
    private LocalDate date;
    private BigDecimal score;
    private Long employeeId;
    private Long reviewerId;
    private String comment;
}
