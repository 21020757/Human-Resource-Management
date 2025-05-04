package org.example.hrm.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ReviewDto {
    private Long id;
    private LocalDate date;
    private BigDecimal score;
    private Long employeeId;
    private String comment;
}
