package org.example.hrm.service;


import org.example.hrm.model.Salary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PayrollService {
    void initMonthlySalary();
    void calculateSalary();
    Page<Salary> getSalary(final Pageable pageable);
    Page<Salary> getSalaryByEmployeeId(final Pageable pageable, Long employeeId);
}
