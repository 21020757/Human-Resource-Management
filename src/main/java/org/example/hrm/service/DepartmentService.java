package org.example.hrm.service;

import org.example.hrm.dto.DepartmentDto;
import org.example.hrm.model.Department;

import java.util.List;

public interface DepartmentService {
    Department getDepartment(Long id);
    Department getDepartmentByName(String departmentName);
    List<Department> getAllDepartment();
    Department createDepartment(DepartmentDto departmentDto);
    void updateDepartment(DepartmentDto departmentDto);
    void deleteDepartment(Long id);
}
