package org.example.hrm.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ContractDto {
    private Long id;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal salary;
    private Boolean active;
    private Long employeeId;
}
