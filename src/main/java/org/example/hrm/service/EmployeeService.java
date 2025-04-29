package org.example.hrm.service;

import org.example.hrm.dto.EmployeeDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {
    EmployeeDto create(EmployeeDto employeeDto);
    EmployeeDto findByEmployeeId(long employeeId);
    EmployeeDto findByEmail(String email);
    void update(Long id, EmployeeDto employeeDto);
    void delete(Long employeeId);
    Page<EmployeeDto> search(String keyword,
                             String position,
                             Long departmentId,
                             Boolean active,
                             Pageable pageable);
}
