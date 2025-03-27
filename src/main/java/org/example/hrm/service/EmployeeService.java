package org.example.hrm.service;

import org.example.hrm.dto.EmployeeDto;

public interface EmployeeService {
    EmployeeDto create(EmployeeDto employeeDto);
    EmployeeDto findByEmployeeId(long employeeId);
}
