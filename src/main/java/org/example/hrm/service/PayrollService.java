package org.example.hrm.service;


import org.example.hrm.dto.SalaryDto;
import org.example.hrm.model.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

public interface PayrollService {
    Page<SalaryDto> getSalaryByEmployee(Long employeeId, int month, int year, Pageable pageable);
    Page<SalaryDto> getSalaryByCurrent(Authentication authentication, int month, int year, Pageable pageable);

    void initMonthlySalary();
    void calculateSalary();
    Page<SalaryDto> getSalary(int month, int year, final Pageable pageable);
    Page<SalaryDto> getSalaryByEmployeeId(final Pageable pageable, Long employeeId);
}
