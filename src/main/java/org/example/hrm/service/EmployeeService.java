package org.example.hrm.service;

import org.example.hrm.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {
    EmployeeDto create(EmployeeDto employeeDto);
    EmployeeDto findByEmployeeId(long employeeId);
    EmployeeDto findByEmail(String email);
    void update(EmployeeDto employeeDto);
    void delete(long employeeId);
    Page<EmployeeDto> search(String keyword,
                             String position,
                             Long departmentId,
                             boolean active,
                             Pageable pageable);
}
