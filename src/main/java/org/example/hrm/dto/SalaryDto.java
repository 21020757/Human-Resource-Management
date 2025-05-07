package org.example.hrm.dto;

import lombok.Data;
import org.example.hrm.model.Salary;

import java.math.BigDecimal;

@Data
public class SalaryDto {
    private Long salaryId;
    private Long employeeId;
    private String employeeName;
    private int month;
    private int year;
    private BigDecimal baseSalary;
    private BigDecimal bonus;
    private BigDecimal deduction;
    private BigDecimal netSalary;

    public SalaryDto(Salary salary) {
        this.salaryId = salary.getId();
        this.employeeId = salary.getEmployee().getId();
        this.employeeName = salary.getEmployee().getFullName();
        this.month = salary.getMonth();
        this.year = salary.getYear();
        this.baseSalary = salary.getBaseSalary();
        this.bonus = salary.getBonus();
        this.deduction = salary.getDeduction();
        this.netSalary = salary.getNetSalary();
    }
}
