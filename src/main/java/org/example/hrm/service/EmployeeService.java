package org.example.hrm.service;

import org.example.hrm.dto.EmployeeDto;
import org.example.hrm.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface EmployeeService {
    Employee create(EmployeeDto employeeDto);
    Employee findByEmployeeId(long employeeId);
    Employee findByEmail(String email);
    void update(Long id, EmployeeDto employeeDto);
    void delete(Long employeeId);
    Page<Employee> search(String keyword,
                             String position,
                             Long departmentId,
                             Boolean active,
                             Pageable pageable);
    Page<Employee> getAll(Boolean hasContract, Pageable pageable);
}
