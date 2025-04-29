package org.example.hrm.service;


import org.example.hrm.dto.SalaryDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayrollService {
    void initMonthlySalary();
    void calculateSalary();
    Page<SalaryDto> getSalary(final Pageable pageable);
    Page<SalaryDto> getSalaryByEmployeeId(final Pageable pageable, Long employeeId);
}
