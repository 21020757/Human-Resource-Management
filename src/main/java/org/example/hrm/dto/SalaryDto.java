package org.example.hrm.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class SalaryDto {
    private int month;
    private int year;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deduction;
    private BigDecimal netSalary;
    private Long employeeId;
}
